package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 一番赏最终赏规则响应。
 */
@Data
@Builder
@Schema(description = "一番赏最终赏规则响应")
public class AdminKujiFinalRuleResponse {

    @Schema(description = "规则 ID")
    private Long id;

    @Schema(description = "活动 ID")
    private Long activityId;

    @Schema(description = "最终赏编码")
    private String finalTierCode;

    @Schema(description = "最终赏名称")
    private String finalRewardName;

    @Schema(description = "触发条件")
    private String triggerCondition;

    @Schema(description = "状态")
    private Integer status;
}
