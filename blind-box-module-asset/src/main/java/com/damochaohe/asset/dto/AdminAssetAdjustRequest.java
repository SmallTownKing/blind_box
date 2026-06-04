package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 后台资产调整请求。
 */
@Data
@Schema(description = "后台资产调整请求")
public class AdminAssetAdjustRequest {

    @NotNull(message = "用户 ID 不能为空")
    @Schema(description = "用户 ID")
    private Long userId;

    @NotBlank(message = "资产类型不能为空")
    @Schema(description = "资产类型：BALANCE/GOLD/MAGIC")
    private String assetType;

    @NotBlank(message = "调整类型不能为空")
    @Schema(description = "调整类型：INCREASE/DECREASE")
    private String adjustType;

    @NotNull(message = "调整数量不能为空")
    @DecimalMin(value = "0.01", message = "调整数量必须大于 0")
    @Schema(description = "调整数量")
    private BigDecimal amount;

    @NotBlank(message = "调整原因不能为空")
    @Schema(description = "调整原因")
    private String reason;
}
