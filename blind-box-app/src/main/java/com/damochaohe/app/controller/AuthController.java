package com.damochaohe.app.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.user.dto.GuestLoginRequest;
import com.damochaohe.user.dto.LoginRequest;
import com.damochaohe.user.dto.LoginResponse;
import com.damochaohe.user.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证相关接口。
 *
 * <p>当前先实现手机号验证码登录接口，后续扩展密码登录、微信登录、游客登录等能力。</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/auth")
@Tag(name = "APP-认证模块")
public class AuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/mobile-code/login")
    @Operation(summary = "手机号验证码登录")
    public ApiResponse<LoginResponse> mobileCodeLogin(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(userAuthService.mobileCodeLogin(request));
    }

    @PostMapping("/guest/login")
    @Operation(summary = "游客登录")
    public ApiResponse<LoginResponse> guestLogin(@Valid @RequestBody GuestLoginRequest request) {
        return ApiResponse.success(userAuthService.guestLogin(request));
    }
}
