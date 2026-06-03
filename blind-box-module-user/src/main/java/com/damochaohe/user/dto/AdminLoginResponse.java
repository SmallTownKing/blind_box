package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 后台管理员登录响应。
 */
@Data
@Builder
@Schema(description = "后台管理员登录响应")
public class AdminLoginResponse {

    @Schema(description = "访问令牌")
    private String accessToken;

    @Schema(description = "令牌类型")
    private String tokenType;

    @Schema(description = "管理员姓名")
    private String name;

    @Schema(description = "角色编码列表")
    private List<String> roles;
}
