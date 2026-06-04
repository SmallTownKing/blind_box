package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "后台奖池发布请求")
public class AdminPoolPublishRequest {

    @NotNull(message = "奖池 ID 不能为空")
    @Schema(description = "奖池 ID")
    private Long poolId;

    @NotNull(message = "单抽售价不能为空")
    @Schema(description = "单抽售价")
    private BigDecimal configuredPricePerDraw;
}
