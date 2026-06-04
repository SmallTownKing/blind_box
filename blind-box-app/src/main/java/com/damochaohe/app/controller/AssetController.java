package com.damochaohe.app.controller;

import com.damochaohe.app.support.AppLoginUserResolver;
import com.damochaohe.asset.dto.AssetOverviewResponse;
import com.damochaohe.asset.dto.AssetFlowResponse;
import com.damochaohe.asset.service.AssetQueryService;
import com.damochaohe.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资产中心接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/asset")
@Tag(name = "APP-资产模块")
public class AssetController {

    private final AssetQueryService assetQueryService;
    private final AppLoginUserResolver appLoginUserResolver;

    @GetMapping("/overview")
    @Operation(summary = "查询用户资产总览")
    public ApiResponse<AssetOverviewResponse> overview(HttpServletRequest request) {
        return ApiResponse.success(assetQueryService.getAssetOverview(appLoginUserResolver.currentUserId(request)));
    }

    @GetMapping("/flows")
    @Operation(summary = "查询用户资产流水")
    public ApiResponse<AssetFlowResponse> flows(HttpServletRequest request) {
        return ApiResponse.success(assetQueryService.getAssetFlows(appLoginUserResolver.currentUserId(request)));
    }
}
