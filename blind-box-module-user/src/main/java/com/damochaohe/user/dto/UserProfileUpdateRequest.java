package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * 用户资料更新请求。
 */
@Data
@Schema(description = "用户资料更新请求")
public class UserProfileUpdateRequest {

    @Size(max = 64, message = "昵称长度不能超过64个字符")
    @Schema(description = "用户昵称", example = "潮玩达人")
    private String nickname;

    @Schema(description = "头像地址", example = "https://static.example.com/avatar/user-10001.png")
    private String avatar;

    @Schema(description = "生日，仅允许首次设置", example = "2000-01-01")
    private LocalDate birthday;

    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱地址", example = "user@example.com")
    private String email;

    @AssertTrue(message = "至少提交一项需要更新的资料")
    @Schema(description = "是否包含有效更新字段", hidden = true)
    public boolean hasAnyUpdate() {
        return (nickname != null && !nickname.isBlank())
                || (avatar != null && !avatar.isBlank())
                || birthday != null
                || (email != null && !email.isBlank());
    }
}
