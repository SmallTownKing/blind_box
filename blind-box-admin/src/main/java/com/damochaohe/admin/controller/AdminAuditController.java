package com.damochaohe.admin.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.play.dto.DrawAuditLogResponse;
import com.damochaohe.play.service.DrawAuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/audit")
@Tag(name = "ADMIN-抽奖审计")
public class AdminAuditController {

    private final DrawAuditLogService drawAuditLogService;

    @GetMapping("/draw-logs")
    @Operation(summary = "查询抽奖审计日志")
    public ApiResponse<List<DrawAuditLogResponse>> drawLogs() {
        return ApiResponse.success(drawAuditLogService.listAuditLogs());
    }
}
