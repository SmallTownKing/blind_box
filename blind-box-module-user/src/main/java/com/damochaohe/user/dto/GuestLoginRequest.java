package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

/**
 * 游客登录请求。
 */
@Data
@Schema(description = "游客登录请求")
public class GuestLoginRequest {

    @Schema(description = "游客设备标识", example = "device-android-001")
    private String deviceId;

    @AssertTrue(message = "请先阅读并同意用户协议与隐私条款")
    @Schema(description = "是否已阅读并同意用户协议与隐私条款", example = "true")
    private Boolean agreedToTerms;
}
