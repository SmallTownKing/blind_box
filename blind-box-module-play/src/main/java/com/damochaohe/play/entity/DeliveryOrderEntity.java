package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("delivery_order")
public class DeliveryOrderEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String deliveryNo;
    private Long userId;
    private Long warehouseItemId;
    private String rewardName;
    private String deliveryStatus;
    private String logisticsCompany;
    private String logisticsNo;
    private String adminRemark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
