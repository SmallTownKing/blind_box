package com.damochaohe.admin.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.content.dto.AdminBannerConfigResponse;
import com.damochaohe.content.dto.AdminBannerSaveRequest;
import com.damochaohe.content.dto.AdminPopupConfigResponse;
import com.damochaohe.content.dto.AdminPopupSaveRequest;
import com.damochaohe.content.dto.AdminSplashConfigResponse;
import com.damochaohe.content.dto.AdminSplashSaveRequest;
import com.damochaohe.content.service.AdminContentManageService;
import com.damochaohe.content.service.AdminContentWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台首页内容配置接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/content")
@Tag(name = "ADMIN-首页内容配置")
public class AdminContentController {

    private final AdminContentManageService adminContentManageService;
    private final AdminContentWriteService adminContentWriteService;

    @GetMapping("/banners")
    @Operation(summary = "查询 Banner 配置列表")
    public ApiResponse<List<AdminBannerConfigResponse>> banners() {
        return ApiResponse.success(adminContentManageService.listBanners());
    }

    @GetMapping("/popups")
    @Operation(summary = "查询首页弹窗配置列表")
    public ApiResponse<List<AdminPopupConfigResponse>> popups() {
        return ApiResponse.success(adminContentManageService.listPopups());
    }

    @GetMapping("/splash-ads")
    @Operation(summary = "查询开屏广告配置列表")
    public ApiResponse<List<AdminSplashConfigResponse>> splashAds() {
        return ApiResponse.success(adminContentManageService.listSplashAds());
    }

    @PostMapping("/banner/save")
    @Operation(summary = "新增或修改 Banner 配置")
    public ApiResponse<Void> saveBanner(@Valid @RequestBody AdminBannerSaveRequest request) {
        adminContentWriteService.saveBanner(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/popup/save")
    @Operation(summary = "新增或修改弹窗配置")
    public ApiResponse<Void> savePopup(@Valid @RequestBody AdminPopupSaveRequest request) {
        adminContentWriteService.savePopup(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/splash-ad/save")
    @Operation(summary = "新增或修改开屏广告配置")
    public ApiResponse<Void> saveSplash(@Valid @RequestBody AdminSplashSaveRequest request) {
        adminContentWriteService.saveSplash(request);
        return ApiResponse.success(null);
    }
}
