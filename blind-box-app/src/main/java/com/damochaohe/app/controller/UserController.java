package com.damochaohe.app.controller;

import com.damochaohe.app.support.AppLoginUserResolver;
import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.user.dto.UserBindMobileRequest;
import com.damochaohe.user.dto.UserPasswordUpdateRequest;
import com.damochaohe.user.dto.UserProfileUpdateRequest;
import com.damochaohe.user.dto.UserProfileResponse;
import com.damochaohe.user.service.UserAuthService;
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

/**
 * 用户资料接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/user")
@Tag(name = "APP-用户模块")
public class UserController {

    private final UserAuthService userAuthService;
    private final AppLoginUserResolver appLoginUserResolver;

    @GetMapping("/profile")
    @Operation(summary = "查询当前用户资料")
    public ApiResponse<UserProfileResponse> profile(HttpServletRequest request) {
        return ApiResponse.success(userAuthService.getUserProfile(appLoginUserResolver.currentUserId(request)));
    }

    @PostMapping("/profile/update")
    @Operation(summary = "更新当前用户资料")
    public ApiResponse<UserProfileResponse> updateProfile(HttpServletRequest httpServletRequest,
                                                          @Valid @RequestBody UserProfileUpdateRequest request) {
        return ApiResponse.success(userAuthService.updateUserProfile(appLoginUserResolver.currentUserId(httpServletRequest), request));
    }

    @PostMapping("/mobile/bind")
    @Operation(summary = "绑定当前用户手机号")
    public ApiResponse<UserProfileResponse> bindMobile(HttpServletRequest httpServletRequest,
                                                       @Valid @RequestBody UserBindMobileRequest request) {
        return ApiResponse.success(userAuthService.bindMobile(appLoginUserResolver.currentUserId(httpServletRequest), request));
    }

    @PostMapping("/password/update")
    @Operation(summary = "设置或修改当前用户登录密码")
    public ApiResponse<Void> updatePassword(HttpServletRequest httpServletRequest,
                                            @Valid @RequestBody UserPasswordUpdateRequest request) {
        userAuthService.updatePassword(appLoginUserResolver.currentUserId(httpServletRequest), request);
        return ApiResponse.success(null);
    }
}
