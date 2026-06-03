package com.damochaohe.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 商品分类响应。
 */
@Data
@Builder
@Schema(description = "商品分类响应")
public class AdminCategoryResponse {

    @Schema(description = "分类 ID")
    private Long id;

    @Schema(description = "父级分类 ID")
    private Long parentId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "分类层级")
    private Integer categoryLevel;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "排序号")
    private Integer sortNo;
}
