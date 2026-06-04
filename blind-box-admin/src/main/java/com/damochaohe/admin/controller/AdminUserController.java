package com.damochaohe.admin.controller;

import com.damochaohe.common.model.PageResponse;
import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.user.dto.AdminUserDetailResponse;
import com.damochaohe.user.dto.AdminUserPageResponse;
import com.damochaohe.user.dto.AdminUserQuery;
import com.damochaohe.user.dto.AdminUserStatusRequest;
import com.damochaohe.user.service.AdminUserService;
import com.damochaohe.user.service.AdminUserWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台用户管理接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
@Tag(name = "ADMIN-用户管理")
public class AdminUserController {

    private final AdminUserService adminUserService;
    private final AdminUserWriteService adminUserWriteService;

    @GetMapping("/page")
    @Operation(summary = "分页查询用户列表")
    public ApiResponse<PageResponse<AdminUserPageResponse>> page(@Valid @ModelAttribute AdminUserQuery query) {
        return ApiResponse.success(adminUserService.pageUsers(query));
    }

    @GetMapping("/detail")
    @Operation(summary = "查询用户详情")
    public ApiResponse<AdminUserDetailResponse> detail(@RequestParam Long userId) {
        return ApiResponse.success(adminUserService.getUserDetail(userId));
    }

    @PostMapping("/status")
    @Operation(summary = "修改用户启用/禁用状态")
    public ApiResponse<Void> updateStatus(@Valid @RequestBody AdminUserStatusRequest request) {
        adminUserWriteService.updateUserStatus(request.getId(), request.getStatus());
        return ApiResponse.success(null);
    }
}
