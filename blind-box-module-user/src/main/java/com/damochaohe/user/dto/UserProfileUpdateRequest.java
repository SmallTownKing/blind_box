package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户资料更新请求。
 */
@Data
@Schema(description = "用户资料更新请求")
public class UserProfileUpdateRequest {

    @NotBlank(message = "昵称不能为空")
    @Schema(description = "用户昵称", example = "潮玩达人")
    private String nickname;

    @Schema(description = "头像地址", example = "https://static.example.com/avatar/user-10001.png")
    private String avatar;
}
