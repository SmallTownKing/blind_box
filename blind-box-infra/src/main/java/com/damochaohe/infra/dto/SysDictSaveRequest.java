package com.damochaohe.infra.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统字典保存请求。
 */
@Data
@Schema(description = "系统字典保存请求")
public class SysDictSaveRequest {

    @Schema(description = "字典项 ID，新增时可为空")
    private Long id;

    @NotBlank(message = "字典类型不能为空")
    @Schema(description = "字典类型")
    private String dictType;

    @NotBlank(message = "字典标签不能为空")
    @Schema(description = "字典标签")
    private String label;

    @NotBlank(message = "字典值不能为空")
    @Schema(description = "字典值")
    private String value;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0停用")
    private Integer status;

    @Schema(description = "排序号")
    private Integer sortNo = 0;

    @Schema(description = "备注")
    private String remark;
}
