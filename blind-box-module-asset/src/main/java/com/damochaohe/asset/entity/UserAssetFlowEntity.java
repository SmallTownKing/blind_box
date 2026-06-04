package com.damochaohe.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("user_asset_flow")
public class UserAssetFlowEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String assetType;
    private String flowType;
    private BigDecimal changeAmount;
    private String bizNo;
    private String remark;
    private LocalDateTime createdAt;
}
