package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户绑定手机号请求。
 */
@Data
@Schema(description = "用户绑定手机号请求")
public class UserBindMobileRequest {

    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号", example = "13800138000")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "短信验证码", example = "123456")
    private String code;

    @AssertTrue(message = "请先阅读并同意用户协议与隐私条款")
    @Schema(description = "是否已阅读并同意用户协议与隐私条款", example = "true")
    private Boolean agreedToTerms;
}
