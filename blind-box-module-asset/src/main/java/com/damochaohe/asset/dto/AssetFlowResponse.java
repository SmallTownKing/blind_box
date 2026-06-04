package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 资产流水响应。
 */
@Data
@Builder
@Schema(description = "资产流水响应")
public class AssetFlowResponse {

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "流水列表")
    private List<AssetFlowItemResponse> items;
}
