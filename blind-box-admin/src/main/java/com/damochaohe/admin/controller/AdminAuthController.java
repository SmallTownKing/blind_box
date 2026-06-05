package com.damochaohe.admin.controller;

import com.damochaohe.admin.support.AdminLoginUserResolver;
import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.user.dto.AdminLoginRequest;
import com.damochaohe.user.dto.AdminLoginResponse;
import com.damochaohe.user.dto.AdminPermissionResponse;
import com.damochaohe.user.service.AdminAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 后台认证接口。
 *
 * <p>当前阶段先提供管理员登录、权限查看、健康检查接口，后续再接入真实管理员表。</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/auth")
@Tag(name = "ADMIN-认证模块")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;
    private final AdminLoginUserResolver adminLoginUserResolver;

    @PostMapping("/login")
    @Operation(summary = "后台管理员登录")
    public ApiResponse<AdminLoginResponse> login(@Valid @RequestBody AdminLoginRequest request) {
        return ApiResponse.success(adminAuthService.login(request));
    }

    @GetMapping("/permissions")
    @Operation(summary = "查询当前管理员权限信息")
    public ApiResponse<AdminPermissionResponse> permissions(HttpServletRequest request) {
        return ApiResponse.success(adminAuthService.getPermissionInfo(adminLoginUserResolver.currentLoginUser(request).getUserId()));
    }

    @GetMapping("/health")
    @Operation(summary = "后台服务健康检查")
    public ApiResponse<Map<String, Object>> health() {
        return ApiResponse.success(Map.of("service", "blind-box-admin", "status", "UP"));
    }
}
