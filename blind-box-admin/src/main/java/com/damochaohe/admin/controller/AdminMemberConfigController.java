package com.damochaohe.admin.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.user.dto.AdminMemberLevelConfigResponse;
import com.damochaohe.user.dto.AdminMemberLevelConfigSaveRequest;
import com.damochaohe.user.service.AdminMemberConfigService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/member-config")
@Tag(name = "ADMIN-会员配置")
public class AdminMemberConfigController {

    private final AdminMemberConfigService adminMemberConfigService;

    @GetMapping("/levels")
    @Operation(summary = "查询会员等级配置列表")
    public ApiResponse<List<AdminMemberLevelConfigResponse>> levels() {
        return ApiResponse.success(adminMemberConfigService.listMemberLevels());
    }

    @PostMapping("/level/save")
    @Operation(summary = "新增或修改会员等级配置")
    public ApiResponse<Void> saveLevel(@Valid @RequestBody AdminMemberLevelConfigSaveRequest request) {
        adminMemberConfigService.saveMemberLevel(request);
        return ApiResponse.success(null);
    }
}
