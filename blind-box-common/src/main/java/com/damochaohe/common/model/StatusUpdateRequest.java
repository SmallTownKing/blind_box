package com.damochaohe.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 通用状态修改请求对象。
 */
@Data
@Schema(description = "通用状态修改请求对象")
public class StatusUpdateRequest {

    @NotNull(message = "ID 不能为空")
    @Schema(description = "主键 ID", example = "1")
    private Long id;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0禁用", example = "1")
    private Integer status;
}
