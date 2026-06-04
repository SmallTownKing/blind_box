package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "后台奖池概率校验请求")
public class AdminPoolProbabilityCheckRequest {

    @NotNull(message = "奖池 ID 不能为空")
    @Schema(description = "奖池 ID")
    private Long poolId;

    @NotNull(message = "单抽售价不能为空")
    @DecimalMin(value = "0.01", message = "单抽售价必须大于 0")
    @Schema(description = "单抽售价")
    private BigDecimal configuredPricePerDraw;
}
