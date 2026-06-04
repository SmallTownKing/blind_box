package com.damochaohe.play.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.asset.dto.AssetOverviewResponse;
import com.damochaohe.asset.entity.UserAssetAccountEntity;
import com.damochaohe.asset.entity.UserAssetFlowEntity;
import com.damochaohe.asset.mapper.UserAssetAccountMapper;
import com.damochaohe.asset.mapper.UserAssetFlowMapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.play.dto.AppDrawRequest;
import com.damochaohe.play.dto.AppDrawResponse;
import com.damochaohe.play.dto.AppDeliveryApplyRequest;
import com.damochaohe.play.dto.AppDeliveryApplyResponse;
import com.damochaohe.play.dto.AppDrawRewardItem;
import com.damochaohe.play.dto.AppHundredDrawPageResponse;
import com.damochaohe.play.dto.PaymentSplitDetail;
import com.damochaohe.play.dto.ProbabilityValidationResult;
import com.damochaohe.play.dto.AppTradeRecordResponse;
import com.damochaohe.play.dto.AppWarehouseItemResponse;
import com.damochaohe.play.entity.PlayDrawRecordEntity;
import com.damochaohe.play.entity.DeliveryOrderEntity;
import com.damochaohe.play.entity.HundredDrawConfigEntity;
import com.damochaohe.play.entity.KujiActivityEntity;
import com.damochaohe.play.entity.KujiFinalRewardRuleEntity;
import com.damochaohe.play.entity.KujiLockRecordEntity;
import com.damochaohe.play.entity.KujiRewardTierEntity;
import com.damochaohe.play.entity.KujiTargetRewardEntity;
import com.damochaohe.play.entity.DrawAuditLogEntity;
import com.damochaohe.play.entity.PlayPoolEntity;
import com.damochaohe.play.entity.PlayPoolRewardEntity;
import com.damochaohe.play.entity.PlayTradeOrderEntity;
import com.damochaohe.play.entity.WarehouseItemEntity;
import com.damochaohe.play.mapper.HundredDrawConfigMapper;
import com.damochaohe.play.mapper.DeliveryOrderMapper;
import com.damochaohe.play.mapper.PlayDrawRecordMapper;
import com.damochaohe.play.mapper.KujiActivityMapper;
import com.damochaohe.play.mapper.KujiFinalRewardRuleMapper;
import com.damochaohe.play.mapper.KujiLockRecordMapper;
import com.damochaohe.play.mapper.KujiRewardTierMapper;
import com.damochaohe.play.mapper.KujiTargetRewardMapper;
import com.damochaohe.play.mapper.DrawAuditLogMapper;
import com.damochaohe.play.mapper.PlayPoolMapper;
import com.damochaohe.play.mapper.PlayPoolRewardMapper;
import com.damochaohe.play.mapper.PlayTradeOrderMapper;
import com.damochaohe.play.mapper.WarehouseItemMapper;
import com.damochaohe.play.service.AppPlayService;
import com.damochaohe.play.service.CommercialPaymentService;
import com.damochaohe.play.service.ProbabilityValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户端玩法服务实现。
 */
@Service
@RequiredArgsConstructor
public class AppPlayServiceImpl implements AppPlayService {

    private final PlayPoolMapper playPoolMapper;
    private final PlayPoolRewardMapper playPoolRewardMapper;
    private final HundredDrawConfigMapper hundredDrawConfigMapper;
    private final UserAssetAccountMapper userAssetAccountMapper;
    private final UserAssetFlowMapper userAssetFlowMapper;
    private final PlayTradeOrderMapper playTradeOrderMapper;
    private final PlayDrawRecordMapper playDrawRecordMapper;
    private final WarehouseItemMapper warehouseItemMapper;
    private final DeliveryOrderMapper deliveryOrderMapper;
    private final ProbabilityValidationService probabilityValidationService;
    private final CommercialPaymentService commercialPaymentService;
    private final SecureRandom secureRandom = new SecureRandom();
    private final KujiActivityMapper kujiActivityMapper;
    private final KujiRewardTierMapper kujiRewardTierMapper;
    private final KujiFinalRewardRuleMapper kujiFinalRewardRuleMapper;
    private final KujiTargetRewardMapper kujiTargetRewardMapper;
    private final KujiLockRecordMapper kujiLockRecordMapper;
    private final DrawAuditLogMapper drawAuditLogMapper;

    @Override
    public AppDrawResponse drawFukubukuro(Long userId, AppDrawRequest request) {
        return doDraw(userId, request, "福袋玩法已完成最小闭环抽赏");
    }

    @Override
    public AppDrawResponse drawKuji(Long userId, AppDrawRequest request) {
        validateKujiEligibility(userId, request.getPoolId());
        return doDraw(userId, request, "一番赏已完成最小闭环抽赏");
    }

    @Override
    public AppHundredDrawPageResponse getHundredDrawPage(Long poolId) {
        HundredDrawConfigEntity entity = hundredDrawConfigMapper.selectList(new LambdaQueryWrapper<HundredDrawConfigEntity>()
                        .eq(HundredDrawConfigEntity::getPoolId, poolId)
                        .eq(HundredDrawConfigEntity::getStatus, 1)
                        .orderByDesc(HundredDrawConfigEntity::getId))
                .stream()
                .findFirst()
                .orElse(null);
        if (entity == null) {
            return AppHundredDrawPageResponse.builder()
                    .id(0L)
                    .poolId(poolId)
                    .pageTitle("百连抽")
                    .pageSubtitle("当前奖池暂未配置百连抽页面，使用默认展示")
                    .bannerUrl("https://static.example.com/banner/hundred-draw-default.png")
                    .build();
        }
        return AppHundredDrawPageResponse.builder()
                .id(entity.getId())
                .poolId(entity.getPoolId())
                .pageTitle(entity.getPageTitle())
                .pageSubtitle(entity.getPageSubtitle())
                .bannerUrl(entity.getBannerUrl())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppDeliveryApplyResponse applyDelivery(Long userId, AppDeliveryApplyRequest request) {
        WarehouseItemEntity warehouseItemEntity = warehouseItemMapper.selectOne(new LambdaQueryWrapper<WarehouseItemEntity>()
                .eq(WarehouseItemEntity::getWarehouseNo, request.getWarehouseNo())
                .eq(WarehouseItemEntity::getUserId, userId)
                .last("limit 1"));
        if (warehouseItemEntity == null) {
            throw new BusinessException("仓库商品不存在");
        }
        if (!"IN_WAREHOUSE".equals(warehouseItemEntity.getItemStatus())) {
            throw new BusinessException("当前商品状态不允许提货");
        }

        String deliveryNo = "DEL" + userId + System.currentTimeMillis();
        DeliveryOrderEntity deliveryOrderEntity = new DeliveryOrderEntity();
        deliveryOrderEntity.setDeliveryNo(deliveryNo);
        deliveryOrderEntity.setUserId(userId);
        deliveryOrderEntity.setWarehouseItemId(warehouseItemEntity.getId());
        deliveryOrderEntity.setRewardName(warehouseItemEntity.getRewardName());
        deliveryOrderEntity.setDeliveryStatus("PENDING");
        deliveryOrderEntity.setCreatedAt(LocalDateTime.now());
        deliveryOrderEntity.setUpdatedAt(LocalDateTime.now());
        deliveryOrderMapper.insert(deliveryOrderEntity);

        warehouseItemEntity.setItemStatus("DELIVERY_PENDING");
        warehouseItemEntity.setUpdatedAt(LocalDateTime.now());
        warehouseItemMapper.updateById(warehouseItemEntity);

        return AppDeliveryApplyResponse.builder()
                .deliveryNo(deliveryNo)
                .warehouseNo(warehouseItemEntity.getWarehouseNo())
                .deliveryStatus("PENDING")
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    private AppDrawResponse doDraw(Long userId, AppDrawRequest request, String summaryPrefix) {
        validateDrawCount(request.getDrawCount());
        KujiExecutionContext kujiExecutionContext = null;
        PlayPoolEntity poolEntity = playPoolMapper.selectById(request.getPoolId());
        if (poolEntity == null || poolEntity.getStatus() == null || poolEntity.getStatus() != 1) {
            throw new BusinessException("奖池不存在或未启用");
        }
        List<PlayPoolRewardEntity> rewards = playPoolRewardMapper.selectList(new LambdaQueryWrapper<PlayPoolRewardEntity>()
                .eq(PlayPoolRewardEntity::getPoolId, request.getPoolId())
                .eq(PlayPoolRewardEntity::getStatus, 1)
                .orderByAsc(PlayPoolRewardEntity::getSortNo)
                .orderByDesc(PlayPoolRewardEntity::getId));
        if (rewards.isEmpty()) {
            throw new BusinessException("当前奖池暂无可抽取奖品");
        }
        if ("KUJI".equalsIgnoreCase(poolEntity.getPoolType())) {
            kujiExecutionContext = buildKujiExecutionContext(userId, request.getPoolId(), request.getDrawCount());
        }

        UserAssetAccountEntity assetAccount = userAssetAccountMapper.selectOne(new LambdaQueryWrapper<UserAssetAccountEntity>()
                .eq(UserAssetAccountEntity::getUserId, userId)
                .last("limit 1"));
        if (assetAccount == null) {
            throw new BusinessException("用户资产账户不存在");
        }

        String payModes = request.getPayModes() == null || request.getPayModes().isBlank() ? poolEntity.getPayModeConfig() : request.getPayModes();
        BigDecimal totalCost = new BigDecimal(request.getDrawCount()).multiply(new BigDecimal("10.00"));
        ProbabilityValidationResult validationResult = probabilityValidationService.validatePoolProbability(request.getPoolId(), new BigDecimal("10.00"));
        if (!Boolean.TRUE.equals(validationResult.getPassed())) {
            throw new BusinessException("奖池概率配置校验未通过：" + validationResult.getMessage());
        }
        String tradeNo = "TRD" + userId + System.currentTimeMillis();
        CommercialPaymentService.CommercialPaymentResult paymentResult = commercialPaymentService.consume(
                userId,
                totalCost,
                parsePayTypes(payModes),
                List.of(),
                tradeNo
        );
        assetAccount = userAssetAccountMapper.selectOne(new LambdaQueryWrapper<UserAssetAccountEntity>()
                .eq(UserAssetAccountEntity::getUserId, userId)
                .last("limit 1"));
        PlayTradeOrderEntity tradeOrderEntity = new PlayTradeOrderEntity();
        tradeOrderEntity.setTradeNo(tradeNo);
        tradeOrderEntity.setUserId(userId);
        tradeOrderEntity.setPoolId(request.getPoolId());
        tradeOrderEntity.setPayModes(payModes);
        tradeOrderEntity.setTotalCost(totalCost);
        tradeOrderEntity.setTradeStatus("SUCCESS");
        tradeOrderEntity.setCreatedAt(LocalDateTime.now());
        tradeOrderEntity.setUpdatedAt(LocalDateTime.now());
        playTradeOrderMapper.insert(tradeOrderEntity);

        List<AppDrawRewardItem> resultRewards = new ArrayList<>();
        List<AppWarehouseItemResponse> warehouseItems = new ArrayList<>();
        for (int i = 0; i < request.getDrawCount(); i++) {
            PlayPoolRewardEntity reward = selectReward(rewards, i, kujiExecutionContext);
            if (reward.getStock() == null || reward.getStock() <= 0) {
                throw new BusinessException("奖品库存不足");
            }
            reward.setStock(reward.getStock() - 1);
            playPoolRewardMapper.updateById(reward);
            resultRewards.add(AppDrawRewardItem.builder()
                    .rewardId(reward.getId())
                    .rewardName(reward.getRewardName())
                    .rewardLevel(reward.getRewardLevel())
                    .poolId(reward.getPoolId())
                    .build());
            warehouseItems.add(AppWarehouseItemResponse.builder()
                    .warehouseNo("WH" + userId + System.currentTimeMillis() + i)
                    .rewardName(reward.getRewardName())
                    .rewardLevel(reward.getRewardLevel())
                    .status("IN_WAREHOUSE")
                    .build());

            PlayDrawRecordEntity drawRecordEntity = new PlayDrawRecordEntity();
            drawRecordEntity.setTradeNo(tradeNo);
            drawRecordEntity.setUserId(userId);
            drawRecordEntity.setPoolId(request.getPoolId());
            drawRecordEntity.setRewardId(reward.getId());
            drawRecordEntity.setRewardName(reward.getRewardName());
            drawRecordEntity.setRewardLevel(reward.getRewardLevel());
            drawRecordEntity.setDrawCount(request.getDrawCount());
            drawRecordEntity.setPayModes(payModes);
            drawRecordEntity.setTotalCost(totalCost);
            drawRecordEntity.setCreatedAt(LocalDateTime.now());
            playDrawRecordMapper.insert(drawRecordEntity);

            WarehouseItemEntity warehouseItemEntity = new WarehouseItemEntity();
            warehouseItemEntity.setWarehouseNo(warehouseItems.get(warehouseItems.size() - 1).getWarehouseNo());
            warehouseItemEntity.setUserId(userId);
            warehouseItemEntity.setTradeNo(tradeNo);
            warehouseItemEntity.setRewardId(reward.getId());
            warehouseItemEntity.setRewardName(reward.getRewardName());
            warehouseItemEntity.setRewardLevel(reward.getRewardLevel());
            warehouseItemEntity.setItemStatus("IN_WAREHOUSE");
            warehouseItemEntity.setCreatedAt(LocalDateTime.now());
            warehouseItemEntity.setUpdatedAt(LocalDateTime.now());
            warehouseItemMapper.insert(warehouseItemEntity);
        }

        if (kujiExecutionContext != null) {
            afterKujiDraw(userId, request.getDrawCount(), kujiExecutionContext);
        }

        DrawAuditLogEntity auditLogEntity = new DrawAuditLogEntity();
        auditLogEntity.setTradeNo(tradeNo);
        auditLogEntity.setUserId(userId);
        auditLogEntity.setPoolId(request.getPoolId());
        auditLogEntity.setProbabilitySnapshot(buildProbabilitySnapshot(rewards));
        auditLogEntity.setTargetRewardHit(kujiExecutionContext != null && kujiExecutionContext.targetRewardConfigId != null ? 1 : 0);
        auditLogEntity.setFinalRewardHit(kujiExecutionContext != null && kujiExecutionContext.triggerFinalReward ? 1 : 0);
        auditLogEntity.setStockChangeSnapshot(buildStockChangeSnapshot(rewards));
        auditLogEntity.setCreatedAt(LocalDateTime.now());
        drawAuditLogMapper.insert(auditLogEntity);

        AssetOverviewResponse assetOverview = AssetOverviewResponse.builder()
                .balanceAmount(assetAccount.getBalanceAmount())
                .goldCoinAmount(assetAccount.getGoldCoinAmount())
                .magicCrystalAmount(assetAccount.getMagicCrystalAmount())
                .build();

        return AppDrawResponse.builder()
                .userId(userId)
                .poolId(request.getPoolId())
                .drawCount(request.getDrawCount())
                .payModes(payModes)
                .totalCost(totalCost)
                .rewards(resultRewards)
                .tradeRecord(AppTradeRecordResponse.builder()
                        .tradeNo(tradeNo)
                        .payModes(payModes)
                        .totalCost(totalCost)
                        .tradeStatus("SUCCESS")
                        .build())
                .warehouseItems(warehouseItems)
                .assetOverview(assetOverview)
                .paymentSplits(List.of(PaymentSplitDetail.builder()
                        .payType(resolvePrimaryAssetType(payModes))
                        .amount(totalCost)
                        .build()))
                .resultSummary(summaryPrefix + "，共获得 " + resultRewards.size() + " 件奖品，已完成真实扣款与入仓")
                .build();
    }

    private String buildProbabilitySnapshot(List<PlayPoolRewardEntity> rewards) {
        return rewards.stream()
                .map(item -> item.getId() + ":" + item.getProbability())
                .reduce((a, b) -> a + "," + b)
                .orElse("");
    }

    private String buildStockChangeSnapshot(List<PlayPoolRewardEntity> rewards) {
        return rewards.stream()
                .map(item -> item.getId() + ":" + item.getStock())
                .reduce((a, b) -> a + "," + b)
                .orElse("");
    }

    private void applyRealAssetDeduction(UserAssetAccountEntity assetAccount, BigDecimal totalCost, String payModes) {
        String mode = payModes == null || payModes.isBlank() ? "BALANCE" : payModes.toUpperCase();
        if (mode.contains("MAGIC")) {
            int deductMagic = totalCost.multiply(new BigDecimal("100")).intValue();
            if (assetAccount.getMagicCrystalAmount() < deductMagic) {
                throw new BusinessException("魔晶余额不足");
            }
            assetAccount.setMagicCrystalAmount(assetAccount.getMagicCrystalAmount() - deductMagic);
        } else if (mode.contains("GOLD")) {
            int deductGold = totalCost.multiply(new BigDecimal("100")).intValue();
            if (assetAccount.getGoldCoinAmount() < deductGold) {
                throw new BusinessException("金币余额不足");
            }
            assetAccount.setGoldCoinAmount(assetAccount.getGoldCoinAmount() - deductGold);
        } else {
            if (assetAccount.getBalanceAmount().compareTo(totalCost) < 0) {
                throw new BusinessException("余额不足");
            }
            assetAccount.setBalanceAmount(assetAccount.getBalanceAmount().subtract(totalCost));
        }
        assetAccount.setUpdatedAt(LocalDateTime.now());
    }

    private String resolvePrimaryAssetType(String payModes) {
        String mode = payModes == null || payModes.isBlank() ? "BALANCE" : payModes.toUpperCase();
        if (mode.contains("MAGIC")) {
            return "MAGIC";
        }
        if (mode.contains("GOLD")) {
            return "GOLD";
        }
        return "BALANCE";
    }

    private PlayPoolRewardEntity selectRewardByProbability(List<PlayPoolRewardEntity> rewards, int sequence) {
        BigDecimal total = rewards.stream()
                .map(item -> item.getProbability() == null ? BigDecimal.ZERO : item.getProbability())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("奖池概率配置非法：总概率必须大于 0");
        }

        BigDecimal randomBase = BigDecimal.valueOf(secureRandom.nextDouble())
                .multiply(total)
                .setScale(8, java.math.RoundingMode.HALF_UP);
        BigDecimal cumulative = BigDecimal.ZERO;
        for (PlayPoolRewardEntity reward : rewards) {
            BigDecimal probability = reward.getProbability() == null ? BigDecimal.ZERO : reward.getProbability();
            cumulative = cumulative.add(probability);
            if (randomBase.compareTo(cumulative) <= 0) {
                return reward;
            }
        }
        return rewards.get(rewards.size() - 1);
    }

    private List<String> parsePayTypes(String payModes) {
        if (payModes == null || payModes.isBlank()) {
            return List.of("BALANCE");
        }
        return java.util.Arrays.stream(payModes.split(",|\\+"))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .toList();
    }

    private PlayPoolRewardEntity selectReward(List<PlayPoolRewardEntity> rewards, int sequence, KujiExecutionContext context) {
        if (context != null && context.targetRewardTierId != null) {
            PlayPoolRewardEntity targetReward = matchRewardByTier(rewards, context.targetRewardTierId, context.targetRewardTierCode);
            if (targetReward != null) {
                return targetReward;
            }
        }
        if (context != null && context.finalRewardTierCode != null && context.triggerFinalReward) {
            PlayPoolRewardEntity finalReward = matchRewardByTier(rewards, null, context.finalRewardTierCode);
            if (finalReward != null) {
                return finalReward;
            }
        }
        return selectRewardByProbability(rewards, sequence);
    }

    private PlayPoolRewardEntity matchRewardByTier(List<PlayPoolRewardEntity> rewards, Long tierId, String tierCode) {
        if (tierId != null) {
            PlayPoolRewardEntity byId = rewards.stream()
                    .filter(item -> tierId.equals(item.getKujiTierId()))
                    .findFirst()
                    .orElse(null);
            if (byId != null) {
                return byId;
            }
        }
        if (tierCode == null || tierCode.isBlank()) {
            return null;
        }
        return rewards.stream()
                .filter(item -> tierCode.equalsIgnoreCase(item.getRewardLevel()))
                .findFirst()
                .orElse(null);
    }

    private KujiExecutionContext buildKujiExecutionContext(Long userId, Long poolId, Integer drawCount) {
        KujiExecutionContext context = new KujiExecutionContext();
        KujiActivityEntity activity = kujiActivityMapper.selectOne(new LambdaQueryWrapper<KujiActivityEntity>()
                .eq(KujiActivityEntity::getId, poolId)
                .eq(KujiActivityEntity::getStatus, 1)
                .last("limit 1"));
        if (activity == null) {
            return context;
        }
        context.activityId = activity.getId();
        context.boxRemainStockBeforeDraw = activity.getBoxRemainStock() == null ? 0 : activity.getBoxRemainStock();

        KujiTargetRewardEntity targetReward = kujiTargetRewardMapper.selectOne(new LambdaQueryWrapper<KujiTargetRewardEntity>()
                .eq(KujiTargetRewardEntity::getActivityId, activity.getId())
                .eq(KujiTargetRewardEntity::getTargetUserId, userId)
                .eq(KujiTargetRewardEntity::getStatus, 1)
                .last("limit 1"));
        if (targetReward != null) {
            context.targetRewardConfigId = targetReward.getId();
            KujiRewardTierEntity rewardTier = kujiRewardTierMapper.selectById(targetReward.getRewardTierId());
            if (rewardTier != null) {
                context.targetRewardTierId = rewardTier.getId();
                context.targetRewardTierCode = rewardTier.getTierCode();
            }
        }

        KujiFinalRewardRuleEntity finalRule = kujiFinalRewardRuleMapper.selectOne(new LambdaQueryWrapper<KujiFinalRewardRuleEntity>()
                .eq(KujiFinalRewardRuleEntity::getActivityId, activity.getId())
                .eq(KujiFinalRewardRuleEntity::getStatus, 1)
                .last("limit 1"));
        if (finalRule != null) {
            context.finalRewardTierCode = finalRule.getFinalTierCode();
            int remainStock = activity.getBoxRemainStock() == null ? 0 : activity.getBoxRemainStock();
            int currentDrawCount = drawCount == null ? 0 : drawCount;
            context.triggerFinalReward = remainStock > 0 && remainStock <= currentDrawCount;
        }
        return context;
    }

    private void afterKujiDraw(Long userId, Integer drawCount, KujiExecutionContext context) {
        KujiActivityEntity activity = kujiActivityMapper.selectById(context.activityId);
        if (activity != null) {
            int remainStock = activity.getBoxRemainStock() == null ? 0 : activity.getBoxRemainStock();
            int used = drawCount == null ? 0 : drawCount;
            activity.setBoxRemainStock(Math.max(0, remainStock - used));
            activity.setUpdatedAt(LocalDateTime.now());
            kujiActivityMapper.updateById(activity);
        }

        if (context.targetRewardConfigId != null) {
            KujiTargetRewardEntity targetReward = kujiTargetRewardMapper.selectById(context.targetRewardConfigId);
            if (targetReward != null) {
                targetReward.setStatus(0);
                targetReward.setUpdatedAt(LocalDateTime.now());
                kujiTargetRewardMapper.updateById(targetReward);
            }
        }

        KujiLockRecordEntity lockRecord = kujiLockRecordMapper.selectOne(new LambdaQueryWrapper<KujiLockRecordEntity>()
                .eq(KujiLockRecordEntity::getActivityId, context.activityId)
                .eq(KujiLockRecordEntity::getUserId, userId)
                .eq(KujiLockRecordEntity::getLockStatus, 1)
                .last("limit 1"));
        if (lockRecord != null) {
            lockRecord.setLockStatus(0);
            lockRecord.setReleaseReason("DRAW_COMPLETED");
            lockRecord.setUpdatedAt(LocalDateTime.now());
            kujiLockRecordMapper.updateById(lockRecord);
        }
    }

    private void validateKujiEligibility(Long userId, Long poolId) {
        KujiActivityEntity activity = kujiActivityMapper.selectOne(new LambdaQueryWrapper<KujiActivityEntity>()
                .eq(KujiActivityEntity::getId, poolId)
                .eq(KujiActivityEntity::getStatus, 1)
                .last("limit 1"));
        if (activity == null) {
            return;
        }

        if (activity.getLockBoxEnabled() != null && activity.getLockBoxEnabled() == 1) {
            KujiLockRecordEntity lockRecord = kujiLockRecordMapper.selectOne(new LambdaQueryWrapper<KujiLockRecordEntity>()
                    .eq(KujiLockRecordEntity::getActivityId, activity.getId())
                    .eq(KujiLockRecordEntity::getUserId, userId)
                    .eq(KujiLockRecordEntity::getLockStatus, 1)
                    .last("limit 1"));
            if (lockRecord == null) {
                throw new BusinessException("当前一番赏已开启锁箱，用户无有效锁箱资格");
            }
        }

        KujiTargetRewardEntity targetReward = kujiTargetRewardMapper.selectOne(new LambdaQueryWrapper<KujiTargetRewardEntity>()
                .eq(KujiTargetRewardEntity::getActivityId, activity.getId())
                .eq(KujiTargetRewardEntity::getTargetUserId, userId)
                .eq(KujiTargetRewardEntity::getStatus, 1)
                .last("limit 1"));
        if (targetReward != null) {
            KujiRewardTierEntity rewardTier = kujiRewardTierMapper.selectById(targetReward.getRewardTierId());
            if (rewardTier != null && rewardTier.getRemainStock() != null && rewardTier.getRemainStock() <= 0) {
                throw new BusinessException("指定中奖奖项库存不足");
            }
        }

        KujiFinalRewardRuleEntity finalRule = kujiFinalRewardRuleMapper.selectOne(new LambdaQueryWrapper<KujiFinalRewardRuleEntity>()
                .eq(KujiFinalRewardRuleEntity::getActivityId, activity.getId())
                .eq(KujiFinalRewardRuleEntity::getStatus, 1)
                .last("limit 1"));
        if (finalRule != null && activity.getBoxRemainStock() != null && activity.getBoxRemainStock() <= 1) {
            // 预留最终赏触发入口。
        }
    }

    private void validateDrawCount(Integer drawCount) {
        if (drawCount == null || !(drawCount == 1 || drawCount == 5 || drawCount == 10)) {
            throw new BusinessException("当前仅支持单抽、5连抽、10连抽");
        }
    }

    private static class KujiExecutionContext {
        private Long activityId;
        private Integer boxRemainStockBeforeDraw;
        private Long targetRewardConfigId;
        private Long targetRewardTierId;
        private String targetRewardTierCode;
        private String finalRewardTierCode;
        private boolean triggerFinalReward;
    }
}
