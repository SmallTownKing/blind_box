package com.damochaohe.play.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.asset.dto.CouponConsumeDetail;
import com.damochaohe.asset.entity.CouponConsumeRecordEntity;
import com.damochaohe.asset.entity.CouponTemplateEntity;
import com.damochaohe.asset.entity.UserAssetAccountEntity;
import com.damochaohe.asset.entity.UserAssetFlowEntity;
import com.damochaohe.asset.entity.UserCouponEntity;
import com.damochaohe.asset.mapper.CouponConsumeRecordMapper;
import com.damochaohe.asset.mapper.CouponTemplateMapper;
import com.damochaohe.asset.mapper.UserAssetAccountMapper;
import com.damochaohe.asset.mapper.UserAssetFlowMapper;
import com.damochaohe.asset.mapper.UserCouponMapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.play.dto.PaymentSplitDetail;
import com.damochaohe.play.service.CommercialPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommercialPaymentServiceImpl implements CommercialPaymentService {

    private final UserAssetAccountMapper userAssetAccountMapper;
    private final UserAssetFlowMapper userAssetFlowMapper;
    private final UserCouponMapper userCouponMapper;
    private final CouponTemplateMapper couponTemplateMapper;
    private final CouponConsumeRecordMapper couponConsumeRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommercialPaymentResult consume(Long userId, BigDecimal totalAmount, List<String> payTypes, List<Long> userCouponIds, String bizNo) {
        UserAssetAccountEntity account = userAssetAccountMapper.selectOne(new LambdaQueryWrapper<UserAssetAccountEntity>()
                .eq(UserAssetAccountEntity::getUserId, userId)
                .last("limit 1"));
        if (account == null) {
            throw new BusinessException("用户资产账户不存在");
        }

        BigDecimal remain = totalAmount;
        List<CouponConsumeDetail> couponDetails = new ArrayList<>();
        if (userCouponIds != null) {
            for (Long userCouponId : userCouponIds) {
                if (remain.compareTo(BigDecimal.ZERO) <= 0) {
                    break;
                }
                UserCouponEntity userCoupon = userCouponMapper.selectById(userCouponId);
                if (userCoupon == null || !userId.equals(userCoupon.getUserId()) || !"UNUSED".equals(userCoupon.getCouponStatus())) {
                    continue;
                }
                CouponTemplateEntity template = couponTemplateMapper.selectById(userCoupon.getTemplateId());
                if (template == null || template.getStatus() == null || template.getStatus() != 1) {
                    continue;
                }
                if (template.getThresholdAmount() != null && remain.compareTo(template.getThresholdAmount()) < 0) {
                    continue;
                }
                BigDecimal discount = template.getDiscountAmount() == null ? BigDecimal.ZERO : template.getDiscountAmount();
                if (discount.compareTo(remain) > 0) {
                    discount = remain;
                }
                remain = remain.subtract(discount);
                userCoupon.setCouponStatus("USED");
                userCoupon.setUsedTime(LocalDateTime.now());
                userCoupon.setUpdatedAt(LocalDateTime.now());
                userCouponMapper.updateById(userCoupon);

                CouponConsumeRecordEntity consumeRecord = new CouponConsumeRecordEntity();
                consumeRecord.setUserCouponId(userCoupon.getId());
                consumeRecord.setUserId(userId);
                consumeRecord.setBizNo(bizNo);
                consumeRecord.setDiscountAmount(discount);
                consumeRecord.setCreatedAt(LocalDateTime.now());
                couponConsumeRecordMapper.insert(consumeRecord);

                couponDetails.add(CouponConsumeDetail.builder()
                        .userCouponId(userCoupon.getId())
                        .couponName(template.getCouponName())
                        .discountAmount(discount)
                        .build());
            }
        }

        List<PaymentSplitDetail> splits = new ArrayList<>();
        List<String> actualPayTypes = (payTypes == null || payTypes.isEmpty()) ? List.of("BALANCE") : payTypes;
        for (String payType : actualPayTypes) {
            if (remain.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
            String upper = payType.toUpperCase();
            if ("BALANCE".equals(upper)) {
                BigDecimal use = account.getBalanceAmount().min(remain);
                if (use.compareTo(BigDecimal.ZERO) > 0) {
                    account.setBalanceAmount(account.getBalanceAmount().subtract(use));
                    remain = remain.subtract(use);
                    splits.add(PaymentSplitDetail.builder().payType("BALANCE").amount(use).build());
                    insertFlow(userId, "BALANCE", use, bizNo, "组合支付扣减");
                }
            } else if ("GOLD".equals(upper)) {
                BigDecimal goldMoney = new BigDecimal(account.getGoldCoinAmount()).divide(new BigDecimal("100"));
                BigDecimal use = goldMoney.min(remain);
                if (use.compareTo(BigDecimal.ZERO) > 0) {
                    int deduct = use.multiply(new BigDecimal("100")).intValue();
                    account.setGoldCoinAmount(account.getGoldCoinAmount() - deduct);
                    remain = remain.subtract(use);
                    splits.add(PaymentSplitDetail.builder().payType("GOLD").amount(use).build());
                    insertFlow(userId, "GOLD", use, bizNo, "组合支付扣减");
                }
            } else if ("MAGIC".equals(upper)) {
                BigDecimal magicMoney = new BigDecimal(account.getMagicCrystalAmount()).divide(new BigDecimal("100"));
                BigDecimal use = magicMoney.min(remain);
                if (use.compareTo(BigDecimal.ZERO) > 0) {
                    int deduct = use.multiply(new BigDecimal("100")).intValue();
                    account.setMagicCrystalAmount(account.getMagicCrystalAmount() - deduct);
                    remain = remain.subtract(use);
                    splits.add(PaymentSplitDetail.builder().payType("MAGIC").amount(use).build());
                    insertFlow(userId, "MAGIC", use, bizNo, "组合支付扣减");
                }
            }
        }

        if (remain.compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessException("可用支付资产不足");
        }
        account.setUpdatedAt(LocalDateTime.now());
        userAssetAccountMapper.updateById(account);

        CommercialPaymentResult result = new CommercialPaymentResult();
        result.payableAmount = totalAmount;
        result.paymentSplits = splits;
        result.couponConsumeDetails = couponDetails;
        return result;
    }

    private void insertFlow(Long userId, String assetType, BigDecimal amount, String bizNo, String remark) {
        UserAssetFlowEntity flowEntity = new UserAssetFlowEntity();
        flowEntity.setUserId(userId);
        flowEntity.setAssetType(assetType);
        flowEntity.setFlowType("EXPENSE");
        flowEntity.setChangeAmount(amount);
        flowEntity.setBizNo(bizNo);
        flowEntity.setRemark(remark);
        flowEntity.setCreatedAt(LocalDateTime.now());
        userAssetFlowMapper.insert(flowEntity);
    }
}
