package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 后台用户列表项。
 */
@Data
@Builder
@Schema(description = "后台用户分页项")
public class AdminUserPageResponse {

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "用户编号")
    private String userNo;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "是否已绑定手机号")
    private Boolean mobileBound;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "生日")
    private LocalDate birthday;

    @Schema(description = "是否已设置登录密码")
    private Boolean passwordSet;

    @Schema(description = "当前会员等级")
    private Integer memberLevel;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "注册来源")
    private String registerSource;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
