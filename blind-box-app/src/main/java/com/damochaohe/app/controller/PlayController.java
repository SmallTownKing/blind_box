package com.damochaohe.app.controller;

import com.damochaohe.app.support.AppLoginUserResolver;
import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.play.dto.AppDrawRequest;
import com.damochaohe.play.dto.AppDrawResponse;
import com.damochaohe.play.dto.AppDeliveryApplyRequest;
import com.damochaohe.play.dto.AppDeliveryApplyResponse;
import com.damochaohe.play.dto.AppHundredDrawPageResponse;
import com.damochaohe.play.service.AppPlayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户端玩法接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/play")
@Tag(name = "APP-玩法模块")
public class PlayController {

    private final AppPlayService appPlayService;
    private final AppLoginUserResolver appLoginUserResolver;

    @PostMapping("/fukubukuro/draw")
    @Operation(summary = "福袋抽赏")
    public ApiResponse<AppDrawResponse> drawFukubukuro(HttpServletRequest httpServletRequest,
                                                       @Valid @RequestBody AppDrawRequest request) {
        return ApiResponse.success(appPlayService.drawFukubukuro(appLoginUserResolver.currentUserId(httpServletRequest), request));
    }

    @PostMapping("/kuji/draw")
    @Operation(summary = "一番赏抽赏")
    public ApiResponse<AppDrawResponse> drawKuji(HttpServletRequest httpServletRequest,
                                                 @Valid @RequestBody AppDrawRequest request) {
        return ApiResponse.success(appPlayService.drawKuji(appLoginUserResolver.currentUserId(httpServletRequest), request));
    }

    @GetMapping("/hundred-draw/page")
    @Operation(summary = "查询百连抽页面数据")
    public ApiResponse<AppHundredDrawPageResponse> hundredDrawPage(
            @Parameter(description = "奖池 ID") @RequestParam Long poolId) {
        return ApiResponse.success(appPlayService.getHundredDrawPage(poolId));
    }

    @PostMapping("/delivery/apply")
    @Operation(summary = "申请提货")
    public ApiResponse<AppDeliveryApplyResponse> applyDelivery(HttpServletRequest httpServletRequest,
                                                               @Valid @RequestBody AppDeliveryApplyRequest request) {
        return ApiResponse.success(appPlayService.applyDelivery(appLoginUserResolver.currentUserId(httpServletRequest), request));
    }
}
