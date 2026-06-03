package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 后台用户列表项。
 */
@Data
@Builder
@Schema(description = "后台用户分页项")
public class AdminUserPageResponse {

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "当前会员等级")
    private Integer memberLevel;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "注册来源")
    private String registerSource;
}
