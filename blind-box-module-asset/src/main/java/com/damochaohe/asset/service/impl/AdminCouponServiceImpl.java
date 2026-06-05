package com.damochaohe.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.asset.dto.AdminCouponConsumeRecordResponse;
import com.damochaohe.asset.dto.AdminCouponIssueRequest;
import com.damochaohe.asset.dto.AdminCouponTemplateResponse;
import com.damochaohe.asset.dto.AdminCouponTemplateSaveRequest;
import com.damochaohe.asset.dto.AdminUserCouponQuery;
import com.damochaohe.asset.dto.AdminUserCouponResponse;
import com.damochaohe.asset.entity.CouponConsumeRecordEntity;
import com.damochaohe.asset.entity.CouponTemplateEntity;
import com.damochaohe.asset.entity.UserCouponEntity;
import com.damochaohe.asset.mapper.CouponConsumeRecordMapper;
import com.damochaohe.asset.mapper.CouponTemplateMapper;
import com.damochaohe.asset.mapper.UserCouponMapper;
import com.damochaohe.asset.service.AdminCouponService;
import com.damochaohe.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService {

    private final CouponTemplateMapper couponTemplateMapper;
    private final UserCouponMapper userCouponMapper;
    private final CouponConsumeRecordMapper couponConsumeRecordMapper;

    @Override
    public List<AdminCouponTemplateResponse> listTemplates() {
        return couponTemplateMapper.selectList(new LambdaQueryWrapper<CouponTemplateEntity>()
                        .orderByDesc(CouponTemplateEntity::getId))
                .stream()
                .map(item -> AdminCouponTemplateResponse.builder()
                        .id(item.getId())
                        .couponName(item.getCouponName())
                        .couponType(item.getCouponType())
                        .discountAmount(item.getDiscountAmount())
                        .thresholdAmount(item.getThresholdAmount())
                        .status(item.getStatus())
                        .build())
                .toList();
    }

    @Override
    public void saveTemplate(AdminCouponTemplateSaveRequest request) {
        CouponTemplateEntity entity = request.getId() == null ? new CouponTemplateEntity() : couponTemplateMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("优惠券模板不存在");
        }
        if (entity == null) {
            entity = new CouponTemplateEntity();
        }
        entity.setCouponName(request.getCouponName());
        entity.setCouponType(request.getCouponType());
        entity.setDiscountAmount(request.getDiscountAmount());
        entity.setThresholdAmount(request.getThresholdAmount());
        entity.setStatus(request.getStatus());
        if (request.getId() == null) {
            couponTemplateMapper.insert(entity);
        } else {
            couponTemplateMapper.updateById(entity);
        }
    }

    @Override
    public String issueCoupon(AdminCouponIssueRequest request) {
        CouponTemplateEntity template = couponTemplateMapper.selectById(request.getTemplateId());
        if (template == null || template.getStatus() == null || template.getStatus() != 1) {
            throw new BusinessException("优惠券模板不存在或未启用");
        }
        String couponCode = "CPN" + request.getUserId() + System.currentTimeMillis();
        UserCouponEntity entity = new UserCouponEntity();
        entity.setUserId(request.getUserId());
        entity.setTemplateId(request.getTemplateId());
        entity.setCouponStatus("UNUSED");
        entity.setCouponCode(couponCode);
        entity.setExpireTime(LocalDateTime.now().plusHours(request.getExpireHours() == null ? 24 * 30L : request.getExpireHours()));
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        userCouponMapper.insert(entity);
        return couponCode;
    }

    @Override
    public List<AdminUserCouponResponse> listUserCoupons(AdminUserCouponQuery query) {
        return userCouponMapper.selectList(new LambdaQueryWrapper<UserCouponEntity>()
                        .eq(query != null && query.getUserId() != null, UserCouponEntity::getUserId, query == null ? null : query.getUserId())
                        .eq(query != null && query.getCouponStatus() != null && !query.getCouponStatus().isBlank(), UserCouponEntity::getCouponStatus, query == null ? null : query.getCouponStatus())
                        .orderByDesc(UserCouponEntity::getId))
                .stream()
                .map(item -> {
                    CouponTemplateEntity template = couponTemplateMapper.selectById(item.getTemplateId());
                    return AdminUserCouponResponse.builder()
                            .id(item.getId())
                            .userId(item.getUserId())
                            .templateId(item.getTemplateId())
                            .couponName(template == null ? null : template.getCouponName())
                            .couponStatus(item.getCouponStatus())
                            .couponCode(item.getCouponCode())
                            .expireTime(item.getExpireTime())
                            .usedTime(item.getUsedTime())
                            .build();
                })
                .toList();
    }

    @Override
    public List<AdminCouponConsumeRecordResponse> listConsumeRecords(Long userId) {
        return couponConsumeRecordMapper.selectList(new LambdaQueryWrapper<CouponConsumeRecordEntity>()
                        .eq(userId != null, CouponConsumeRecordEntity::getUserId, userId)
                        .orderByDesc(CouponConsumeRecordEntity::getId))
                .stream()
                .map(item -> {
                    UserCouponEntity userCoupon = userCouponMapper.selectById(item.getUserCouponId());
                    CouponTemplateEntity template = userCoupon == null ? null : couponTemplateMapper.selectById(userCoupon.getTemplateId());
                    return AdminCouponConsumeRecordResponse.builder()
                            .id(item.getId())
                            .userCouponId(item.getUserCouponId())
                            .userId(item.getUserId())
                            .couponName(template == null ? null : template.getCouponName())
                            .bizNo(item.getBizNo())
                            .discountAmount(item.getDiscountAmount())
                            .createdAt(item.getCreatedAt())
                            .build();
                })
                .toList();
    }
}
