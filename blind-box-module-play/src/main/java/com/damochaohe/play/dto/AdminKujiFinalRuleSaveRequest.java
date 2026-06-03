package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 一番赏最终赏规则保存请求。
 */
@Data
@Schema(description = "一番赏最终赏规则保存请求")
public class AdminKujiFinalRuleSaveRequest {

    @Schema(description = "规则 ID，新增时为空")
    private Long id;

    @NotNull(message = "活动 ID 不能为空")
    @Schema(description = "活动 ID")
    private Long activityId;

    @NotBlank(message = "最终赏编码不能为空")
    @Schema(description = "最终赏编码")
    private String finalTierCode;

    @NotBlank(message = "最终赏名称不能为空")
    @Schema(description = "最终赏名称")
    private String finalRewardName;

    @NotBlank(message = "触发条件不能为空")
    @Schema(description = "触发条件，如 LAST_ONE")
    private String triggerCondition;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0停用")
    private Integer status;
}
