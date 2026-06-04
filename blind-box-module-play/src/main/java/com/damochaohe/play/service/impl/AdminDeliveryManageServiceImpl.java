package com.damochaohe.play.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.play.dto.AdminDeliveryUpdateRequest;
import com.damochaohe.play.entity.DeliveryOrderEntity;
import com.damochaohe.play.entity.WarehouseItemEntity;
import com.damochaohe.play.mapper.DeliveryOrderMapper;
import com.damochaohe.play.mapper.WarehouseItemMapper;
import com.damochaohe.play.service.AdminDeliveryManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminDeliveryManageServiceImpl implements AdminDeliveryManageService {

    private final DeliveryOrderMapper deliveryOrderMapper;
    private final WarehouseItemMapper warehouseItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeliveryStatus(AdminDeliveryUpdateRequest request) {
        DeliveryOrderEntity deliveryOrderEntity = deliveryOrderMapper.selectOne(new LambdaQueryWrapper<DeliveryOrderEntity>()
                .eq(DeliveryOrderEntity::getDeliveryNo, request.getDeliveryNo())
                .last("limit 1"));
        if (deliveryOrderEntity == null) {
            throw new BusinessException("提货单不存在");
        }
        WarehouseItemEntity warehouseItemEntity = warehouseItemMapper.selectById(deliveryOrderEntity.getWarehouseItemId());
        if (warehouseItemEntity == null) {
            throw new BusinessException("仓库商品不存在");
        }

        String targetStatus = request.getDeliveryStatus().toUpperCase();
        deliveryOrderEntity.setDeliveryStatus(targetStatus);
        deliveryOrderEntity.setLogisticsCompany(request.getLogisticsCompany());
        deliveryOrderEntity.setLogisticsNo(request.getLogisticsNo());
        deliveryOrderEntity.setAdminRemark(request.getAdminRemark());
        deliveryOrderEntity.setUpdatedAt(LocalDateTime.now());
        deliveryOrderMapper.updateById(deliveryOrderEntity);

        if ("SHIPPED".equals(targetStatus)) {
            warehouseItemEntity.setItemStatus("SHIPPED");
        } else if ("COMPLETED".equals(targetStatus)) {
            warehouseItemEntity.setItemStatus("DELIVERED");
        } else if ("CANCELLED".equals(targetStatus)) {
            warehouseItemEntity.setItemStatus("IN_WAREHOUSE");
        } else {
            throw new BusinessException("不支持的提货状态");
        }
        warehouseItemEntity.setUpdatedAt(LocalDateTime.now());
        warehouseItemMapper.updateById(warehouseItemEntity);
    }
}
