package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资产流水项响应。
 */
@Data
@Builder
@Schema(description = "资产流水项响应")
public class AssetFlowItemResponse {

    @Schema(description = "流水类型")
    private String flowType;

    @Schema(description = "资产类型")
    private String assetType;

    @Schema(description = "变动金额")
    private BigDecimal amount;

    @Schema(description = "变动说明")
    private String remark;

    @Schema(description = "创建时间")
    private String createdAt;
}
