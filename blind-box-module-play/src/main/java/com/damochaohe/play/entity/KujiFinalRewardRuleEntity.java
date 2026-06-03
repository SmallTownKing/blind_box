package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 一番赏最终赏规则实体。
 */
@Data
@TableName("kuji_final_reward_rule")
public class KujiFinalRewardRuleEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long activityId;

    private String finalTierCode;

    private String finalRewardName;

    private String triggerCondition;

    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
