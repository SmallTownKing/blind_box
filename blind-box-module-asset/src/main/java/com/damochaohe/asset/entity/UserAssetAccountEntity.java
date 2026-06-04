package com.damochaohe.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("user_asset_account")
public class UserAssetAccountEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private BigDecimal balanceAmount;
    private Integer goldCoinAmount;
    private Integer magicCrystalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
