package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 一番赏指定中奖与特殊赏保存请求。
 */
@Data
@Schema(description = "一番赏指定中奖与特殊赏保存请求")
public class AdminKujiTargetRewardSaveRequest {

    @Schema(description = "配置 ID，新增时为空")
    private Long id;

    @NotNull(message = "活动 ID 不能为空")
    @Schema(description = "活动 ID")
    private Long activityId;

    @NotNull(message = "用户 ID 不能为空")
    @Schema(description = "指定用户 ID")
    private Long targetUserId;

    @NotNull(message = "目标用户类型不能为空")
    @Schema(description = "目标用户类型：1真实用户 2机器人")
    private Integer targetUserType;

    @Schema(description = "机器人标识，当 targetUserType=2 时可填写")
    private String robotIdentity;

    @NotNull(message = "奖项层级 ID 不能为空")
    @Schema(description = "奖项层级 ID")
    private Long rewardTierId;

    @NotNull(message = "特殊赏开关不能为空")
    @Schema(description = "是否特殊赏：1是 0否")
    private Integer specialRewardEnabled;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0停用")
    private Integer status;
}
