package com.damochaohe.admin.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.play.dto.AppDrawResponse;
import com.damochaohe.play.dto.AppTradeRecordResponse;
import com.damochaohe.play.dto.AppWarehouseItemResponse;
import com.damochaohe.play.dto.AdminDeliveryQuery;
import com.damochaohe.play.dto.AdminDeliveryRecordResponse;
import com.damochaohe.play.service.AdminPlayRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台玩法记录接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/play-records")
@Tag(name = "ADMIN-玩法记录")
public class AdminPlayRecordController {

    private final AdminPlayRecordService adminPlayRecordService;

    @GetMapping("/draws")
    @Operation(summary = "查询抽赏记录")
    public ApiResponse<List<AppDrawResponse>> draws() {
        return ApiResponse.success(adminPlayRecordService.listDrawRecords());
    }

    @GetMapping("/trades")
    @Operation(summary = "查询交易记录")
    public ApiResponse<List<AppTradeRecordResponse>> trades() {
        return ApiResponse.success(adminPlayRecordService.listTradeRecords());
    }

    @GetMapping("/warehouses")
    @Operation(summary = "查询入仓记录")
    public ApiResponse<List<AppWarehouseItemResponse>> warehouses() {
        return ApiResponse.success(adminPlayRecordService.listWarehouseRecords());
    }

    @GetMapping("/deliveries")
    @Operation(summary = "查询提货记录")
    public ApiResponse<List<AdminDeliveryRecordResponse>> deliveries(@ModelAttribute AdminDeliveryQuery query) {
        return ApiResponse.success(adminPlayRecordService.listDeliveryRecords(query));
    }
}
