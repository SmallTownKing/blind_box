package com.damochaohe.user.dto;

import com.damochaohe.common.model.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 后台用户分页查询参数。
 */
@Data
@Schema(description = "后台用户分页查询参数")
public class AdminUserQuery extends BasePageQuery {

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "用户状态")
    private Integer status;

    @Schema(description = "注册来源")
    private String registerSource;

    @Schema(description = "是否游客账号：1是 0否")
    private Integer guestUser;

    @Schema(description = "是否已绑定手机号：1是 0否")
    private Integer mobileBound;

    @Schema(description = "是否已设置登录密码：1是 0否")
    private Integer passwordSet;
}
