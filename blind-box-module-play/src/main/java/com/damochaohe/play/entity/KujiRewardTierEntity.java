package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 一番赏奖项层级实体。
 */
@Data
@TableName("kuji_reward_tier")
public class KujiRewardTierEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long activityId;

    private String tierCode;

    private String tierName;

    private BigDecimal probability;

    private Integer totalStock;

    private Integer remainStock;

    private Integer status;

    private Integer specialRewardEnabled;

    private Integer sortNo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
