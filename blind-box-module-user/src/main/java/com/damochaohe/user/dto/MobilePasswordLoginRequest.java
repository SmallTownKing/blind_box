package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 手机号密码登录请求。
 */
@Data
@Schema(description = "手机号密码登录请求")
public class MobilePasswordLoginRequest {

    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号", example = "13800138000")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 32, message = "密码长度需在6到32位之间")
    @Schema(description = "登录密码", example = "123456")
    private String password;

    @AssertTrue(message = "请先阅读并同意用户协议与隐私条款")
    @Schema(description = "是否已阅读并同意用户协议与隐私条款", example = "true")
    private Boolean agreedToTerms;
}
