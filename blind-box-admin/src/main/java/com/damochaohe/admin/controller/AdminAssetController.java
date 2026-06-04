package com.damochaohe.admin.controller;

import com.damochaohe.asset.dto.AssetFlowResponse;
import com.damochaohe.asset.dto.AssetOverviewResponse;
import com.damochaohe.asset.dto.AdminAssetAdjustRequest;
import com.damochaohe.asset.dto.AdminAssetAdjustResponse;
import com.damochaohe.asset.service.AssetQueryService;
import com.damochaohe.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台用户资产接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/assets")
@Tag(name = "ADMIN-用户资产")
public class AdminAssetController {

    private final AssetQueryService assetQueryService;

    @GetMapping("/overview")
    @Operation(summary = "查询用户资产总览")
    public ApiResponse<AssetOverviewResponse> overview(
            @Parameter(description = "用户 ID") @RequestParam Long userId) {
        return ApiResponse.success(assetQueryService.getAssetOverview(userId));
    }

    @GetMapping("/flows")
    @Operation(summary = "查询用户资产流水")
    public ApiResponse<AssetFlowResponse> flows(
            @Parameter(description = "用户 ID") @RequestParam Long userId) {
        return ApiResponse.success(assetQueryService.getAssetFlows(userId));
    }

    @PostMapping("/adjust")
    @Operation(summary = "后台人工调整用户资产")
    public ApiResponse<AdminAssetAdjustResponse> adjust(@Valid @RequestBody AdminAssetAdjustRequest request) {
        return ApiResponse.success(assetQueryService.adminAdjustAsset(request));
    }
}
