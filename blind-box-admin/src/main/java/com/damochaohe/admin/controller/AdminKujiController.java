package com.damochaohe.admin.controller;

import com.damochaohe.common.model.IdRequest;
import com.damochaohe.common.model.PageResponse;
import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.play.dto.AdminKujiActivityResponse;
import com.damochaohe.play.dto.AdminKujiActivitySaveRequest;
import com.damochaohe.play.dto.AdminKujiActivityStatusRequest;
import com.damochaohe.play.dto.AdminKujiFinalRuleResponse;
import com.damochaohe.play.dto.AdminKujiFinalRuleQuery;
import com.damochaohe.play.dto.AdminKujiFinalRuleSaveRequest;
import com.damochaohe.play.dto.AdminKujiLockResponse;
import com.damochaohe.play.dto.AdminKujiLockSaveRequest;
import com.damochaohe.play.dto.AdminKujiTargetRewardResponse;
import com.damochaohe.play.dto.AdminKujiTargetRewardQuery;
import com.damochaohe.play.dto.AdminKujiTargetRewardSaveRequest;
import com.damochaohe.play.dto.AdminKujiTierResponse;
import com.damochaohe.play.dto.AdminKujiTierSaveRequest;
import com.damochaohe.play.dto.AdminKujiTierStatusRequest;
import com.damochaohe.play.service.AdminKujiManageService;
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

/**
 * 一番赏后台配置接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/kuji")
@Tag(name = "ADMIN-一番赏配置")
public class AdminKujiController {

    private final AdminKujiManageService adminKujiManageService;

    @GetMapping("/activities")
    @Operation(summary = "查询一番赏活动列表")
    public ApiResponse<List<AdminKujiActivityResponse>> activities() {
        return ApiResponse.success(adminKujiManageService.listActivities());
    }

    @PostMapping("/activity/save")
    @Operation(summary = "新增或修改一番赏活动")
    public ApiResponse<Void> saveActivity(@Valid @RequestBody AdminKujiActivitySaveRequest request) {
        adminKujiManageService.saveActivity(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/activity/status")
    @Operation(summary = "修改一番赏活动状态")
    public ApiResponse<Void> updateActivityStatus(@Valid @RequestBody AdminKujiActivityStatusRequest request) {
        adminKujiManageService.updateActivityStatus(request.getId(), request.getStatus());
        return ApiResponse.success(null);
    }

    @PostMapping("/activity/delete")
    @Operation(summary = "删除一番赏活动")
    public ApiResponse<Void> deleteActivity(@Valid @RequestBody IdRequest request) {
        adminKujiManageService.deleteActivity(request.getId());
        return ApiResponse.success(null);
    }

    @GetMapping("/tiers")
    @Operation(summary = "查询一番赏奖项层级列表")
    public ApiResponse<List<AdminKujiTierResponse>> tiers(
            @Parameter(description = "活动 ID") @RequestParam Long activityId) {
        return ApiResponse.success(adminKujiManageService.listTiers(activityId));
    }

    @PostMapping("/tier/save")
    @Operation(summary = "新增或修改一番赏奖项层级")
    public ApiResponse<Void> saveTier(@Valid @RequestBody AdminKujiTierSaveRequest request) {
        adminKujiManageService.saveTier(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/tier/status")
    @Operation(summary = "修改一番赏奖项层级状态")
    public ApiResponse<Void> updateTierStatus(@Valid @RequestBody AdminKujiTierStatusRequest request) {
        adminKujiManageService.updateTierStatus(request.getId(), request.getStatus());
        return ApiResponse.success(null);
    }

    @PostMapping("/tier/delete")
    @Operation(summary = "删除一番赏奖项层级")
    public ApiResponse<Void> deleteTier(@Valid @RequestBody IdRequest request) {
        adminKujiManageService.deleteTier(request.getId());
        return ApiResponse.success(null);
    }

    @GetMapping("/final-rules/page")
    @Operation(summary = "分页查询最终赏规则列表")
    public ApiResponse<PageResponse<AdminKujiFinalRuleResponse>> finalRules(@Valid @ModelAttribute AdminKujiFinalRuleQuery query) {
        return ApiResponse.success(adminKujiManageService.pageFinalRules(query));
    }

    @PostMapping("/final-rule/save")
    @Operation(summary = "新增或修改最终赏规则")
    public ApiResponse<Void> saveFinalRule(@Valid @RequestBody AdminKujiFinalRuleSaveRequest request) {
        adminKujiManageService.saveFinalRule(request);
        return ApiResponse.success(null);
    }

    @GetMapping("/locks")
    @Operation(summary = "查询一番赏锁箱记录")
    public ApiResponse<List<AdminKujiLockResponse>> locks(
            @Parameter(description = "活动 ID") @RequestParam Long activityId) {
        return ApiResponse.success(adminKujiManageService.listLocks(activityId));
    }

    @PostMapping("/lock/save")
    @Operation(summary = "新增或修改一番赏锁箱配置")
    public ApiResponse<Void> saveLock(@Valid @RequestBody AdminKujiLockSaveRequest request) {
        adminKujiManageService.saveLock(request);
        return ApiResponse.success(null);
    }

    @GetMapping("/target-rewards/page")
    @Operation(summary = "分页查询指定中奖与特殊赏配置")
    public ApiResponse<PageResponse<AdminKujiTargetRewardResponse>> targetRewards(@Valid @ModelAttribute AdminKujiTargetRewardQuery query) {
        return ApiResponse.success(adminKujiManageService.pageTargetRewards(query));
    }

    @PostMapping("/target-reward/save")
    @Operation(summary = "新增或修改指定中奖与特殊赏配置")
    public ApiResponse<Void> saveTargetReward(@Valid @RequestBody AdminKujiTargetRewardSaveRequest request) {
        adminKujiManageService.saveTargetReward(request);
        return ApiResponse.success(null);
    }
}
