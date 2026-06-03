package com.damochaohe.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 系统字典项响应。
 */
@Data
@Builder
@Schema(description = "系统字典项响应")
public class SysDictItemResponse {

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "字典标签")
    private String label;

    @Schema(description = "字典值")
    private String value;

    @Schema(description = "排序号")
    private Integer sortNo;
}
