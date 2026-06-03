package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 后台权限信息响应。
 */
@Data
@Builder
@Schema(description = "后台权限信息响应")
public class AdminPermissionResponse {

    @Schema(description = "角色编码列表")
    private List<String> roles;

    @Schema(description = "权限标识列表")
    private List<String> permissions;
}
