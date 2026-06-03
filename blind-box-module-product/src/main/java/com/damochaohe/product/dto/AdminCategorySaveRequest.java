package com.damochaohe.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商品分类保存请求。
 */
@Data
@Schema(description = "商品分类保存请求")
public class AdminCategorySaveRequest {

    @Schema(description = "分类 ID，新增时为空")
    private Long id;

    @Schema(description = "父级分类 ID")
    private Long parentId = 0L;

    @NotBlank(message = "分类名称不能为空")
    @Schema(description = "分类名称")
    private String categoryName;

    @NotNull(message = "分类层级不能为空")
    @Schema(description = "分类层级")
    private Integer categoryLevel;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0停用")
    private Integer status;

    @Schema(description = "排序号")
    private Integer sortNo = 0;
}
