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
}
