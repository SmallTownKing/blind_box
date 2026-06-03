package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 一番赏奖项层级响应。
 */
@Data
@Builder
@Schema(description = "一番赏奖项层级响应")
public class AdminKujiTierResponse {

    @Schema(description = "层级 ID")
    private Long id;

    @Schema(description = "活动 ID")
    private Long activityId;

    @Schema(description = "奖项编码")
    private String tierCode;

    @Schema(description = "奖项名称")
    private String tierName;

    @Schema(description = "概率")
    private BigDecimal probability;

    @Schema(description = "总库存")
    private Integer totalStock;

    @Schema(description = "剩余库存")
    private Integer remainStock;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "是否特殊赏")
    private Integer specialRewardEnabled;
}
