package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 用户资料响应对象。
 */
@Data
@Builder
@Schema(description = "用户资料响应")
public class UserProfileResponse {

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "头像地址")
    private String avatar;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "当前会员等级")
    private Integer currentMemberLevel;

    @Schema(description = "注册来源")
    private String registerSource;

    @Schema(description = "是否游客账号")
    private Boolean guestUser;
}
