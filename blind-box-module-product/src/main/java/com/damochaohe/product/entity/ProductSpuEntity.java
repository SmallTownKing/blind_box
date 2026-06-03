package com.damochaohe.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品 SPU 实体。
 */
@Data
@TableName("product_spu")
public class ProductSpuEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long categoryId;

    private String productName;

    private String productType;

    private String coverUrl;

    private BigDecimal costPrice;

    private BigDecimal marketPrice;

    private Integer status;

    private Integer sortNo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
