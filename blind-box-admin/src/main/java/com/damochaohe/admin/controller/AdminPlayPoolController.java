package com.damochaohe.admin.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.common.model.IdRequest;
import com.damochaohe.play.dto.AdminPlayPoolResponse;
import com.damochaohe.play.dto.AdminFukubukuroRuleResponse;
import com.damochaohe.play.dto.AdminFukubukuroRuleSaveRequest;
import com.damochaohe.play.dto.AdminFukubukuroRuleStatusRequest;
import com.damochaohe.play.dto.AdminHundredDrawResponse;
import com.damochaohe.play.dto.AdminHundredDrawSaveRequest;
import com.damochaohe.play.dto.AdminHundredDrawStatusRequest;
import com.damochaohe.play.dto.AdminPlayPoolRewardResponse;
import com.damochaohe.play.dto.AdminPlayPoolRewardSaveRequest;
import com.damochaohe.play.dto.AdminPlayPoolSaveRequest;
import com.damochaohe.play.dto.AdminPlayPoolRewardStatusRequest;
import com.damochaohe.play.dto.AdminPlayPoolStatusRequest;
import com.damochaohe.play.service.AdminPlayPoolManageService;
import com.damochaohe.play.service.AdminPlayPoolExtraWriteService;
import com.damochaohe.play.service.AdminPlayPoolWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台奖池配置接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/play-pool")
@Tag(name = "ADMIN-奖池配置")
public class AdminPlayPoolController {

    private final AdminPlayPoolManageService adminPlayPoolManageService;
    private final AdminPlayPoolWriteService adminPlayPoolWriteService;
    private final AdminPlayPoolExtraWriteService adminPlayPoolExtraWriteService;

    @GetMapping("/list")
    @Operation(summary = "查询奖池配置列表")
    public ApiResponse<List<AdminPlayPoolResponse>> list() {
        return ApiResponse.success(adminPlayPoolManageService.listPools());
    }

    @PostMapping("/save")
    @Operation(summary = "新增或修改奖池基础配置")
    public ApiResponse<Void> save(@Valid @RequestBody AdminPlayPoolSaveRequest request) {
        adminPlayPoolManageService.savePool(request);
        return ApiResponse.success(null);
    }

    @GetMapping("/rewards")
    @Operation(summary = "查询奖池奖品项列表")
    public ApiResponse<List<AdminPlayPoolRewardResponse>> rewards(
            @Parameter(description = "奖池 ID") @RequestParam Long poolId) {
        return ApiResponse.success(adminPlayPoolManageService.listRewards(poolId));
    }

    @PostMapping("/reward/save")
    @Operation(summary = "新增或修改奖池奖品项配置")
    public ApiResponse<Void> saveReward(@Valid @RequestBody AdminPlayPoolRewardSaveRequest request) {
        adminPlayPoolManageService.saveReward(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/status")
    @Operation(summary = "修改奖池状态")
    public ApiResponse<Void> updatePoolStatus(@Valid @RequestBody AdminPlayPoolStatusRequest request) {
        adminPlayPoolWriteService.updatePoolStatus(request.getId(), request.getStatus());
        return ApiResponse.success(null);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除奖池配置")
    public ApiResponse<Void> deletePool(@Valid @RequestBody IdRequest request) {
        adminPlayPoolWriteService.deletePool(request.getId());
        return ApiResponse.success(null);
    }

    @PostMapping("/reward/status")
    @Operation(summary = "修改奖池奖品项状态")
    public ApiResponse<Void> updateRewardStatus(@Valid @RequestBody AdminPlayPoolRewardStatusRequest request) {
        adminPlayPoolWriteService.updateRewardStatus(request.getId(), request.getStatus());
        return ApiResponse.success(null);
    }

    @PostMapping("/reward/delete")
    @Operation(summary = "删除奖池奖品项")
    public ApiResponse<Void> deleteReward(@Valid @RequestBody IdRequest request) {
        adminPlayPoolWriteService.deleteReward(request.getId());
        return ApiResponse.success(null);
    }

    @GetMapping("/fukubukuro-rules")
    @Operation(summary = "查询福袋玩法详细规则")
    public ApiResponse<List<AdminFukubukuroRuleResponse>> fukubukuroRules(
            @Parameter(description = "奖池 ID") @RequestParam Long poolId) {
        return ApiResponse.success(adminPlayPoolManageService.listFukubukuroRules(poolId));
    }

    @PostMapping("/fukubukuro-rule/save")
    @Operation(summary = "新增或修改福袋玩法详细规则")
    public ApiResponse<Void> saveFukubukuroRule(@Valid @RequestBody AdminFukubukuroRuleSaveRequest request) {
        adminPlayPoolManageService.saveFukubukuroRule(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/fukubukuro-rule/status")
    @Operation(summary = "修改福袋玩法规则状态")
    public ApiResponse<Void> updateFukubukuroRuleStatus(@Valid @RequestBody AdminFukubukuroRuleStatusRequest request) {
        adminPlayPoolExtraWriteService.updateFukubukuroRuleStatus(request.getId(), request.getStatus());
        return ApiResponse.success(null);
    }

    @PostMapping("/fukubukuro-rule/delete")
    @Operation(summary = "删除福袋玩法规则")
    public ApiResponse<Void> deleteFukubukuroRule(@Valid @RequestBody IdRequest request) {
        adminPlayPoolExtraWriteService.deleteFukubukuroRule(request.getId());
        return ApiResponse.success(null);
    }

    @GetMapping("/hundred-draw-configs")
    @Operation(summary = "查询百连抽页面配置")
    public ApiResponse<List<AdminHundredDrawResponse>> hundredDrawConfigs(
            @Parameter(description = "奖池 ID") @RequestParam Long poolId) {
        return ApiResponse.success(adminPlayPoolManageService.listHundredDrawConfigs(poolId));
    }

    @PostMapping("/hundred-draw-config/save")
    @Operation(summary = "新增或修改百连抽页面配置")
    public ApiResponse<Void> saveHundredDrawConfig(@Valid @RequestBody AdminHundredDrawSaveRequest request) {
        adminPlayPoolManageService.saveHundredDrawConfig(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/hundred-draw-config/status")
    @Operation(summary = "修改百连抽页面配置状态")
    public ApiResponse<Void> updateHundredDrawConfigStatus(@Valid @RequestBody AdminHundredDrawStatusRequest request) {
        adminPlayPoolExtraWriteService.updateHundredDrawConfigStatus(request.getId(), request.getStatus());
        return ApiResponse.success(null);
    }

    @PostMapping("/hundred-draw-config/delete")
    @Operation(summary = "删除百连抽页面配置")
    public ApiResponse<Void> deleteHundredDrawConfig(@Valid @RequestBody IdRequest request) {
        adminPlayPoolExtraWriteService.deleteHundredDrawConfig(request.getId());
        return ApiResponse.success(null);
    }
}
