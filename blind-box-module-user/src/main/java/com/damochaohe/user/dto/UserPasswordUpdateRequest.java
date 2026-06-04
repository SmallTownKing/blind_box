package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户登录密码设置/修改请求。
 */
@Data
@Schema(description = "用户登录密码设置/修改请求")
public class UserPasswordUpdateRequest {

    @Schema(description = "旧密码，首次设置时可为空", example = "old123456")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 32, message = "新密码长度需在6到32位之间")
    @Schema(description = "新密码", example = "new123456")
    private String newPassword;

    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认密码", example = "new123456")
    private String confirmPassword;

    @AssertTrue(message = "两次输入的新密码不一致")
    @Schema(description = "确认密码是否一致", hidden = true)
    public boolean isPasswordConfirmed() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
}
