package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 一番赏指定中奖与特殊赏响应。
 */
@Data
@Builder
@Schema(description = "一番赏指定中奖与特殊赏响应")
public class AdminKujiTargetRewardResponse {

    @Schema(description = "配置 ID")
    private Long id;

    @Schema(description = "活动 ID")
    private Long activityId;

    @Schema(description = "指定用户 ID")
    private Long targetUserId;

    @Schema(description = "目标用户类型：1真实用户 2机器人")
    private Integer targetUserType;

    @Schema(description = "机器人标识")
    private String robotIdentity;

    @Schema(description = "奖项层级 ID")
    private Long rewardTierId;

    @Schema(description = "是否特殊赏")
    private Integer specialRewardEnabled;

    @Schema(description = "状态")
    private Integer status;
}
