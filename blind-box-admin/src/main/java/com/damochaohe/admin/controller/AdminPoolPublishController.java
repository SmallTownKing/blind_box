package com.damochaohe.admin.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.play.dto.AdminPoolPublishRequest;
import com.damochaohe.play.service.AdminPoolPublishService;
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
@RequestMapping("/api/admin/play-pool-publish")
@Tag(name = "ADMIN-奖池发布")
public class AdminPoolPublishController {

    private final AdminPoolPublishService adminPoolPublishService;

    @PostMapping("/publish")
    @Operation(summary = "发布奖池")
    public ApiResponse<Void> publish(@Valid @RequestBody AdminPoolPublishRequest request) {
        adminPoolPublishService.publish(request);
        return ApiResponse.success(null);
    }
}
