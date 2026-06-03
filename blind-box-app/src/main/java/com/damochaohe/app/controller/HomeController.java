package com.damochaohe.app.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.content.dto.HomeConfigResponse;
import com.damochaohe.content.service.HomeContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页配置接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/home")
@Tag(name = "APP-首页模块")
public class HomeController {

    private final HomeContentService homeContentService;

    @GetMapping("/config")
    @Operation(summary = "查询首页聚合配置")
    public ApiResponse<HomeConfigResponse> getHomeConfig() {
        return ApiResponse.success(homeContentService.getHomeConfig());
    }
}
