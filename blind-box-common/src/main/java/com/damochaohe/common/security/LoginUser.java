package com.damochaohe.common.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 当前登录用户上下文。
 *
 * <p>该对象用于承载 JWT 解析后的最小用户信息，避免在各业务模块重复定义。</p>
 */
@Data
@Builder
@Schema(description = "当前登录用户信息")
public class LoginUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "登录账号")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "令牌类型，如 app/admin")
    private String tokenType;

    @Schema(description = "角色编码列表")
    private List<String> roles;
}
