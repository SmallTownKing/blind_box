package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 一番赏指定中奖与特殊赏实体。
 */
@Data
@TableName("kuji_target_reward")
public class KujiTargetRewardEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long activityId;

    private Long targetUserId;

    private Long rewardTierId;

    private Integer specialRewardEnabled;

    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
