package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户资产总览。
 */
@Data
@Builder
@Schema(description = "资产总览响应")
public class AssetOverviewResponse {

    @Schema(description = "余额，单位元")
    private BigDecimal balanceAmount;

    @Schema(description = "金币数量")
    private Integer goldCoinAmount;

    @Schema(description = "魔晶数量")
    private Integer magicCrystalAmount;
}
