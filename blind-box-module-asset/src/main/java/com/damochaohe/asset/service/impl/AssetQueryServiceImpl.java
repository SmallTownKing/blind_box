package com.damochaohe.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.asset.dto.AdminAssetAdjustRequest;
import com.damochaohe.asset.dto.AdminAssetAdjustResponse;
import com.damochaohe.asset.dto.AssetFlowItemResponse;
import com.damochaohe.asset.dto.AssetFlowResponse;
import com.damochaohe.asset.dto.AssetOverviewResponse;
import com.damochaohe.asset.entity.UserAssetAccountEntity;
import com.damochaohe.asset.entity.UserAssetFlowEntity;
import com.damochaohe.asset.mapper.UserAssetAccountMapper;
import com.damochaohe.asset.mapper.UserAssetFlowMapper;
import com.damochaohe.asset.service.AssetQueryService;
import com.damochaohe.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 资产查询服务实现。
 *
 * <p>当前为演示数据，后续接入统一账户表 [`user_asset_account`](backend-system-design.md)。</p>
 */
@Service
@RequiredArgsConstructor
public class AssetQueryServiceImpl implements AssetQueryService {

    private final UserAssetAccountMapper userAssetAccountMapper;
    private final UserAssetFlowMapper userAssetFlowMapper;

    @Override
    public AssetOverviewResponse getAssetOverview(Long userId) {
        UserAssetAccountEntity entity = userAssetAccountMapper.selectOne(new LambdaQueryWrapper<UserAssetAccountEntity>()
                .eq(UserAssetAccountEntity::getUserId, userId)
                .last("limit 1"));
        if (entity == null) {
            throw new BusinessException("用户资产账户不存在");
        }
        return AssetOverviewResponse.builder()
                .balanceAmount(entity.getBalanceAmount())
                .goldCoinAmount(entity.getGoldCoinAmount())
                .magicCrystalAmount(entity.getMagicCrystalAmount())
                .build();
    }

    @Override
    public AssetFlowResponse getAssetFlows(Long userId) {
        List<AssetFlowItemResponse> items = userAssetFlowMapper.selectList(new LambdaQueryWrapper<UserAssetFlowEntity>()
                        .eq(UserAssetFlowEntity::getUserId, userId)
                        .orderByDesc(UserAssetFlowEntity::getId))
                .stream()
                .map(item -> AssetFlowItemResponse.builder()
                        .flowType(item.getFlowType())
                        .assetType(item.getAssetType())
                        .amount(item.getChangeAmount())
                        .remark(item.getRemark())
                        .createdAt(String.valueOf(item.getCreatedAt()))
                        .build())
                .toList();
        return AssetFlowResponse.builder()
                .userId(userId)
                .items(items)
                .build();
    }

    @Override
    public AssetOverviewResponse simulateDeductForDraw(Long userId, BigDecimal totalCost, String payModes) {
        UserAssetAccountEntity entity = userAssetAccountMapper.selectOne(new LambdaQueryWrapper<UserAssetAccountEntity>()
                .eq(UserAssetAccountEntity::getUserId, userId)
                .last("limit 1"));
        if (entity == null) {
            throw new BusinessException("用户资产账户不存在");
        }

        BigDecimal originBalance = entity.getBalanceAmount();
        int originGold = entity.getGoldCoinAmount();
        int originMagic = entity.getMagicCrystalAmount();

        String mode = payModes == null || payModes.isBlank() ? "BALANCE" : payModes.toUpperCase();
        BigDecimal balanceAmount = originBalance;
        int goldCoinAmount = originGold;
        int magicCrystalAmount = originMagic;

        if (mode.contains("MAGIC")) {
            int deductMagic = totalCost.multiply(new BigDecimal("100")).intValue();
            magicCrystalAmount = Math.max(0, originMagic - deductMagic);
        } else if (mode.contains("GOLD")) {
            int deductGold = totalCost.multiply(new BigDecimal("100")).intValue();
            goldCoinAmount = Math.max(0, originGold - deductGold);
        } else {
            balanceAmount = originBalance.subtract(totalCost).max(BigDecimal.ZERO);
        }

        return AssetOverviewResponse.builder()
                .balanceAmount(balanceAmount)
                .goldCoinAmount(goldCoinAmount)
                .magicCrystalAmount(magicCrystalAmount)
                .build();
    }

    @Override
    public AdminAssetAdjustResponse adminAdjustAsset(AdminAssetAdjustRequest request) {
        UserAssetAccountEntity entity = userAssetAccountMapper.selectOne(new LambdaQueryWrapper<UserAssetAccountEntity>()
                .eq(UserAssetAccountEntity::getUserId, request.getUserId())
                .last("limit 1"));
        if (entity == null) {
            throw new BusinessException("用户资产账户不存在");
        }
        String assetType = request.getAssetType().toUpperCase();
        String adjustType = request.getAdjustType().toUpperCase();
        BigDecimal amount = request.getAmount();

        if ("BALANCE".equals(assetType)) {
            entity.setBalanceAmount(applyDecimalAdjust(entity.getBalanceAmount(), amount, adjustType));
        } else if ("GOLD".equals(assetType)) {
            entity.setGoldCoinAmount(applyIntegerAdjust(entity.getGoldCoinAmount(), amount.intValue(), adjustType));
        } else if ("MAGIC".equals(assetType)) {
            entity.setMagicCrystalAmount(applyIntegerAdjust(entity.getMagicCrystalAmount(), amount.intValue(), adjustType));
        } else {
            throw new BusinessException("不支持的资产类型");
        }

        entity.setUpdatedAt(LocalDateTime.now());
        userAssetAccountMapper.updateById(entity);

        UserAssetFlowEntity flowEntity = new UserAssetFlowEntity();
        flowEntity.setUserId(request.getUserId());
        flowEntity.setAssetType(assetType);
        flowEntity.setFlowType("INCREASE".equals(adjustType) ? "ADMIN_INCREASE" : "ADMIN_DECREASE");
        flowEntity.setChangeAmount(amount);
        flowEntity.setBizNo("ADMIN-ADJUST-" + System.currentTimeMillis());
        flowEntity.setRemark(request.getReason());
        flowEntity.setCreatedAt(LocalDateTime.now());
        userAssetFlowMapper.insert(flowEntity);

        return AdminAssetAdjustResponse.builder()
                .userId(request.getUserId())
                .assetType(assetType)
                .adjustType(adjustType)
                .reason(request.getReason())
                .assetOverview(AssetOverviewResponse.builder()
                        .balanceAmount(entity.getBalanceAmount())
                        .goldCoinAmount(entity.getGoldCoinAmount())
                        .magicCrystalAmount(entity.getMagicCrystalAmount())
                        .build())
                .build();
    }

    private BigDecimal applyDecimalAdjust(BigDecimal current, BigDecimal amount, String adjustType) {
        if ("DECREASE".equals(adjustType)) {
            return current.subtract(amount).max(BigDecimal.ZERO);
        }
        return current.add(amount);
    }

    private int applyIntegerAdjust(int current, int amount, String adjustType) {
        if ("DECREASE".equals(adjustType)) {
            return Math.max(0, current - amount);
        }
        return current + amount;
    }
}
