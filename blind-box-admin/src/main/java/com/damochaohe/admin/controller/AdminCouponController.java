package com.damochaohe.admin.controller;

import com.damochaohe.asset.dto.AdminCouponConsumeRecordResponse;
import com.damochaohe.asset.dto.AdminCouponIssueRequest;
import com.damochaohe.asset.dto.AdminCouponTemplateResponse;
import com.damochaohe.asset.dto.AdminCouponTemplateSaveRequest;
import com.damochaohe.asset.dto.AdminUserCouponQuery;
import com.damochaohe.asset.dto.AdminUserCouponResponse;
import com.damochaohe.asset.service.AdminCouponService;
import com.damochaohe.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/coupons")
@Tag(name = "ADMIN-优惠券管理")
public class AdminCouponController {

    private final AdminCouponService adminCouponService;

    @GetMapping("/templates")
    @Operation(summary = "查询优惠券模板列表")
    public ApiResponse<List<AdminCouponTemplateResponse>> templates() {
        return ApiResponse.success(adminCouponService.listTemplates());
    }

    @GetMapping("/user-coupons")
    @Operation(summary = "查询用户优惠券列表")
    public ApiResponse<List<AdminUserCouponResponse>> userCoupons(@ModelAttribute AdminUserCouponQuery query) {
        return ApiResponse.success(adminCouponService.listUserCoupons(query));
    }

    @GetMapping("/consume-records")
    @Operation(summary = "查询优惠券核销记录")
    public ApiResponse<List<AdminCouponConsumeRecordResponse>> consumeRecords(
            @Parameter(description = "用户 ID，可为空表示查询全部") @RequestParam(required = false) Long userId) {
        return ApiResponse.success(adminCouponService.listConsumeRecords(userId));
    }

    @PostMapping("/template/save")
    @Operation(summary = "新增或修改优惠券模板")
    public ApiResponse<Void> saveTemplate(@Valid @RequestBody AdminCouponTemplateSaveRequest request) {
        adminCouponService.saveTemplate(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/issue")
    @Operation(summary = "后台发放优惠券")
    public ApiResponse<String> issue(@Valid @RequestBody AdminCouponIssueRequest request) {
        return ApiResponse.success(adminCouponService.issueCoupon(request));
    }
}
