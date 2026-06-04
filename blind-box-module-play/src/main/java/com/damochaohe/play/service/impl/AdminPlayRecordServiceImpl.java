package com.damochaohe.play.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.asset.dto.AssetOverviewResponse;
import com.damochaohe.asset.entity.UserAssetAccountEntity;
import com.damochaohe.asset.mapper.UserAssetAccountMapper;
import com.damochaohe.play.dto.AppDrawResponse;
import com.damochaohe.play.dto.AppDrawRewardItem;
import com.damochaohe.play.dto.AppTradeRecordResponse;
import com.damochaohe.play.dto.AppWarehouseItemResponse;
import com.damochaohe.play.dto.AdminDeliveryQuery;
import com.damochaohe.play.dto.AdminDeliveryRecordResponse;
import com.damochaohe.play.entity.DeliveryOrderEntity;
import com.damochaohe.play.entity.PlayDrawRecordEntity;
import com.damochaohe.play.entity.PlayTradeOrderEntity;
import com.damochaohe.play.entity.WarehouseItemEntity;
import com.damochaohe.play.mapper.DeliveryOrderMapper;
import com.damochaohe.play.mapper.PlayDrawRecordMapper;
import com.damochaohe.play.mapper.PlayTradeOrderMapper;
import com.damochaohe.play.mapper.WarehouseItemMapper;
import com.damochaohe.play.service.AdminPlayRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台玩法记录查询实现。
 */
@Service
@RequiredArgsConstructor
public class AdminPlayRecordServiceImpl implements AdminPlayRecordService {

    private final PlayDrawRecordMapper playDrawRecordMapper;
    private final PlayTradeOrderMapper playTradeOrderMapper;
    private final WarehouseItemMapper warehouseItemMapper;
    private final DeliveryOrderMapper deliveryOrderMapper;
    private final UserAssetAccountMapper userAssetAccountMapper;

    @Override
    public List<AppDrawResponse> listDrawRecords() {
        return playDrawRecordMapper.selectList(new LambdaQueryWrapper<PlayDrawRecordEntity>()
                        .orderByDesc(PlayDrawRecordEntity::getId))
                .stream()
                .map(item -> AppDrawResponse.builder()
                        .userId(item.getUserId())
                        .poolId(item.getPoolId())
                        .drawCount(item.getDrawCount())
                        .payModes(item.getPayModes())
                        .totalCost(item.getTotalCost())
                        .rewards(List.of(AppDrawRewardItem.builder()
                                .rewardId(item.getRewardId())
                                .rewardName(item.getRewardName())
                                .rewardLevel(item.getRewardLevel())
                                .poolId(item.getPoolId())
                                .build()))
                        .tradeRecord(findTradeRecord(item.getTradeNo()))
                        .warehouseItems(findWarehouseItems(item.getTradeNo()))
                        .assetOverview(findAssetOverview(item.getUserId()))
                        .resultSummary("真实抽赏记录")
                        .build())
                .toList();
    }

    @Override
    public List<AppTradeRecordResponse> listTradeRecords() {
        return playTradeOrderMapper.selectList(new LambdaQueryWrapper<PlayTradeOrderEntity>()
                        .orderByDesc(PlayTradeOrderEntity::getId))
                .stream()
                .map(this::toTradeRecord)
                .toList();
    }

    @Override
    public List<AppWarehouseItemResponse> listWarehouseRecords() {
        return warehouseItemMapper.selectList(new LambdaQueryWrapper<WarehouseItemEntity>()
                        .orderByDesc(WarehouseItemEntity::getId))
                .stream()
                .map(this::toWarehouseItem)
                .toList();
    }

    @Override
    public List<AdminDeliveryRecordResponse> listDeliveryRecords(AdminDeliveryQuery query) {
        LambdaQueryWrapper<DeliveryOrderEntity> wrapper = new LambdaQueryWrapper<DeliveryOrderEntity>()
                .orderByDesc(DeliveryOrderEntity::getId);
        if (query != null && query.getUserId() != null) {
            wrapper.eq(DeliveryOrderEntity::getUserId, query.getUserId());
        }
        if (query != null && query.getDeliveryStatus() != null && !query.getDeliveryStatus().isBlank()) {
            wrapper.eq(DeliveryOrderEntity::getDeliveryStatus, query.getDeliveryStatus());
        }
        return deliveryOrderMapper.selectList(wrapper)
                .stream()
                .map(item -> AdminDeliveryRecordResponse.builder()
                        .deliveryNo(item.getDeliveryNo())
                        .userId(item.getUserId())
                        .rewardName(item.getRewardName())
                        .deliveryStatus(item.getDeliveryStatus())
                        .build())
                .toList();
    }

    private AppTradeRecordResponse findTradeRecord(String tradeNo) {
        PlayTradeOrderEntity entity = playTradeOrderMapper.selectOne(new LambdaQueryWrapper<PlayTradeOrderEntity>()
                .eq(PlayTradeOrderEntity::getTradeNo, tradeNo)
                .last("limit 1"));
        return entity == null ? null : toTradeRecord(entity);
    }

    private AppTradeRecordResponse toTradeRecord(PlayTradeOrderEntity entity) {
        return AppTradeRecordResponse.builder()
                .tradeNo(entity.getTradeNo())
                .payModes(entity.getPayModes())
                .totalCost(entity.getTotalCost())
                .tradeStatus(entity.getTradeStatus())
                .build();
    }

    private List<AppWarehouseItemResponse> findWarehouseItems(String tradeNo) {
        return warehouseItemMapper.selectList(new LambdaQueryWrapper<WarehouseItemEntity>()
                        .eq(WarehouseItemEntity::getTradeNo, tradeNo)
                        .orderByDesc(WarehouseItemEntity::getId))
                .stream()
                .map(this::toWarehouseItem)
                .toList();
    }

    private AppWarehouseItemResponse toWarehouseItem(WarehouseItemEntity entity) {
        return AppWarehouseItemResponse.builder()
                .warehouseNo(entity.getWarehouseNo())
                .rewardName(entity.getRewardName())
                .rewardLevel(entity.getRewardLevel())
                .status(entity.getItemStatus())
                .build();
    }

    private AssetOverviewResponse findAssetOverview(Long userId) {
        UserAssetAccountEntity entity = userAssetAccountMapper.selectOne(new LambdaQueryWrapper<UserAssetAccountEntity>()
                .eq(UserAssetAccountEntity::getUserId, userId)
                .last("limit 1"));
        if (entity == null) {
            return null;
        }
        return AssetOverviewResponse.builder()
                .balanceAmount(entity.getBalanceAmount())
                .goldCoinAmount(entity.getGoldCoinAmount())
                .magicCrystalAmount(entity.getMagicCrystalAmount())
                .build();
    }
}
