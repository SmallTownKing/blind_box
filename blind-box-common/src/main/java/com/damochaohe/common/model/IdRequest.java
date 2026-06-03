package com.damochaohe.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 通用 ID 请求对象。
 */
@Data
@Schema(description = "通用 ID 请求对象")
public class IdRequest {

    @NotNull(message = "ID 不能为空")
    @Schema(description = "主键 ID", example = "1")
    private Long id;
}
