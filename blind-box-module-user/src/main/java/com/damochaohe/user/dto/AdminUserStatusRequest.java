package com.damochaohe.user.dto;

import com.damochaohe.common.model.StatusUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 后台用户状态修改请求。
 */
@Schema(description = "后台用户状态修改请求")
public class AdminUserStatusRequest extends StatusUpdateRequest {
}
