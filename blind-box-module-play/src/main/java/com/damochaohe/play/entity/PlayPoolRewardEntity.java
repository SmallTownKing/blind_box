package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 奖池奖品项配置实体。
 */
@Data
@TableName("play_pool_reward")
public class PlayPoolRewardEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long poolId;

    private Long productId;

    private Long kujiTierId;

    private String rewardName;

    private String rewardLevel;

    private BigDecimal probability;

    private Integer stock;

    private Integer status;

    private Integer sortNo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
