package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 奖池奖品项保存请求。
 */
@Data
@Schema(description = "奖池奖品项保存请求")
public class AdminPlayPoolRewardSaveRequest {

    @Schema(description = "奖品项 ID，新增时为空")
    private Long id;

    @NotNull(message = "奖池 ID 不能为空")
    @Schema(description = "奖池 ID")
    private Long poolId;

    @NotNull(message = "商品 ID 不能为空")
    @Schema(description = "商品 ID")
    private Long productId;

    @NotBlank(message = "奖品名称不能为空")
    @Schema(description = "奖品名称")
    private String rewardName;

    @Schema(description = "奖项层级")
    private String rewardLevel;

    @NotNull(message = "中奖概率不能为空")
    @Schema(description = "中奖概率")
    private BigDecimal probability;

    @NotNull(message = "库存不能为空")
    @Schema(description = "库存")
    private Integer stock;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0停用")
    private Integer status;

    @Schema(description = "排序号")
    private Integer sortNo = 0;
}
