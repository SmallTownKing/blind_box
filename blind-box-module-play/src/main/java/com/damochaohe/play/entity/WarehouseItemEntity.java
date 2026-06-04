package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("warehouse_item")
public class WarehouseItemEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String warehouseNo;
    private Long userId;
    private String tradeNo;
    private Long rewardId;
    private String rewardName;
    private String rewardLevel;
    private String itemStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
