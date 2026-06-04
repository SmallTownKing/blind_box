package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "概率校验结果")
public class ProbabilityValidationResult {

    @Schema(description = "是否通过校验")
    private Boolean passed;

    @Schema(description = "概率总和")
    private BigDecimal probabilityTotal;

    @Schema(description = "理论单抽成本")
    private BigDecimal expectedCostPerDraw;

    @Schema(description = "配置单抽售价")
    private BigDecimal configuredPricePerDraw;

    @Schema(description = "理论毛利")
    private BigDecimal grossMarginPerDraw;

    @Schema(description = "结果说明")
    private String message;
}
