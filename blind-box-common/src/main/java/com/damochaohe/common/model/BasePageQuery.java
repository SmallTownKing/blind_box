package com.damochaohe.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * 通用分页查询参数。
 *
 * <p>所有分页接口优先继承该对象，保证分页字段统一。</p>
 */
@Data
@Schema(description = "分页查询基础参数")
public class BasePageQuery {

    @Min(value = 1, message = "页码最小为 1")
    @Schema(description = "页码", example = "1")
    private Long pageNum = 1L;

    @Min(value = 1, message = "每页条数最小为 1")
    @Max(value = 100, message = "每页条数最大为 100")
    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
