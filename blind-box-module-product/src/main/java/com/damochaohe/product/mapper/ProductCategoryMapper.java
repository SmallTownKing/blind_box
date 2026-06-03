package com.damochaohe.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damochaohe.product.entity.ProductCategoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品分类 Mapper。
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategoryEntity> {
}
