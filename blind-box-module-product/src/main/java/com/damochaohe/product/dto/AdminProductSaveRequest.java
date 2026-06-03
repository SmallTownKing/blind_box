package com.damochaohe.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品保存请求。
 */
@Data
@Schema(description = "商品保存请求")
public class AdminProductSaveRequest {

    @Schema(description = "商品 ID，新增时为空")
    private Long id;

    @NotNull(message = "分类 ID 不能为空")
    @Schema(description = "分类 ID")
    private Long categoryId;

    @NotBlank(message = "商品名称不能为空")
    @Schema(description = "商品名称")
    private String productName;

    @NotBlank(message = "商品类型不能为空")
    @Schema(description = "商品类型：TOY/CARD/VIRTUAL")
    private String productType;

    @Schema(description = "封面图地址")
    private String coverUrl;

    @Schema(description = "成本价")
    private BigDecimal costPrice;

    @Schema(description = "市场价")
    private BigDecimal marketPrice;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0停用")
    private Integer status;

    @Schema(description = "排序号")
    private Integer sortNo = 0;
}
