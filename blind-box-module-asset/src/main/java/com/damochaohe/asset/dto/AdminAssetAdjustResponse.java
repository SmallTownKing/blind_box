package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 后台资产调整响应。
 */
@Data
@Builder
@Schema(description = "后台资产调整响应")
public class AdminAssetAdjustResponse {

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "资产类型")
    private String assetType;

    @Schema(description = "调整类型")
    private String adjustType;

    @Schema(description = "调整原因")
    private String reason;

    @Schema(description = "调整后资产快照")
    private AssetOverviewResponse assetOverview;
}
