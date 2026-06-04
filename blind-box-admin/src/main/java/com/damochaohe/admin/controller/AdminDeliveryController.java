package com.damochaohe.admin.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.play.dto.AdminDeliveryUpdateRequest;
import com.damochaohe.play.service.AdminDeliveryManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/deliveries")
@Tag(name = "ADMIN-提货管理")
public class AdminDeliveryController {

    private final AdminDeliveryManageService adminDeliveryManageService;

    @PostMapping("/status")
    @Operation(summary = "更新提货状态")
    public ApiResponse<Void> updateStatus(@Valid @RequestBody AdminDeliveryUpdateRequest request) {
        adminDeliveryManageService.updateDeliveryStatus(request);
        return ApiResponse.success(null);
    }
}
