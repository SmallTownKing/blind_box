package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 一番赏奖项层级保存请求。
 */
@Data
@Schema(description = "一番赏奖项层级保存请求")
public class AdminKujiTierSaveRequest {

    @Schema(description = "层级 ID，新增时为空")
    private Long id;

    @NotNull(message = "活动 ID 不能为空")
    @Schema(description = "活动 ID")
    private Long activityId;

    @NotBlank(message = "奖项编码不能为空")
    @Schema(description = "奖项编码")
    private String tierCode;

    @NotBlank(message = "奖项名称不能为空")
    @Schema(description = "奖项名称")
    private String tierName;

    @NotNull(message = "概率不能为空")
    @Schema(description = "概率")
    private BigDecimal probability;

    @NotNull(message = "总库存不能为空")
    @Schema(description = "总库存")
    private Integer totalStock;

    @NotNull(message = "剩余库存不能为空")
    @Schema(description = "剩余库存")
    private Integer remainStock;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "是否特殊赏：1是 0否")
    private Integer specialRewardEnabled = 0;

    @Schema(description = "排序号")
    private Integer sortNo = 0;
}
