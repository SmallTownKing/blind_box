package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 手机号验证码登录请求。
 *
 * <p>当前阶段先实现最小登录参数模型，后续可补充图形验证码、设备号、渠道号等字段。</p>
 */
@Data
@Schema(description = "手机号验证码登录请求")
public class LoginRequest {

    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号", example = "13800138000")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "短信验证码", example = "123456")
    private String code;
}
