package com.damochaohe.admin.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.play.dto.AdminPoolProbabilityCheckRequest;
import com.damochaohe.play.dto.ProbabilityValidationResult;
import com.damochaohe.play.service.ProbabilityValidationService;
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
@RequestMapping("/api/admin/play-risk")
@Tag(name = "ADMIN-奖池风控")
public class AdminPlayRiskController {

    private final ProbabilityValidationService probabilityValidationService;

    @PostMapping("/probability/check")
    @Operation(summary = "校验奖池概率与理论毛利")
    public ApiResponse<ProbabilityValidationResult> checkProbability(@Valid @RequestBody AdminPoolProbabilityCheckRequest request) {
        return ApiResponse.success(probabilityValidationService.validatePoolProbability(request.getPoolId(), request.getConfiguredPricePerDraw()));
    }
}
