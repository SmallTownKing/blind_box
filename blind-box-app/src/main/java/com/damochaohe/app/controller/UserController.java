package com.damochaohe.app.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.user.dto.UserProfileUpdateRequest;
import com.damochaohe.user.dto.UserProfileResponse;
import com.damochaohe.user.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户资料接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/user")
@Tag(name = "APP-用户模块")
public class UserController {

    private final UserAuthService userAuthService;

    @GetMapping("/profile")
    @Operation(summary = "查询当前用户资料")
    public ApiResponse<UserProfileResponse> profile() {
        return ApiResponse.success(userAuthService.getUserProfile(10001L));
    }

    @PostMapping("/profile/update")
    @Operation(summary = "更新当前用户资料")
    public ApiResponse<UserProfileResponse> updateProfile(@Valid @RequestBody UserProfileUpdateRequest request) {
        return ApiResponse.success(userAuthService.updateUserProfile(10001L, request));
    }
}
