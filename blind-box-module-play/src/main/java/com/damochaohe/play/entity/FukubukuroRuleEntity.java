package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 福袋玩法详细规则实体。
 */
@Data
@TableName("fukubukuro_rule")
public class FukubukuroRuleEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long poolId;

    private String rarityType;

    private String noHitTip;

    private BigDecimal autoSelectPriceLimit;

    private Integer status;

    private Integer sortNo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
