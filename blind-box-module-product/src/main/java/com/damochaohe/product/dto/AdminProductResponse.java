package com.damochaohe.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品响应。
 */
@Data
@Builder
@Schema(description = "商品响应")
public class AdminProductResponse {

    @Schema(description = "商品 ID")
    private Long id;

    @Schema(description = "分类 ID")
    private Long categoryId;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "商品类型")
    private String productType;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "成本价")
    private BigDecimal costPrice;

    @Schema(description = "市场价")
    private BigDecimal marketPrice;

    @Schema(description = "状态")
    private Integer status;
}
