package com.damochaohe.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.product.dto.AdminCategoryResponse;
import com.damochaohe.product.dto.AdminCategorySaveRequest;
import com.damochaohe.product.dto.AdminProductResponse;
import com.damochaohe.product.dto.AdminProductSaveRequest;
import com.damochaohe.product.entity.ProductCategoryEntity;
import com.damochaohe.product.entity.ProductSpuEntity;
import com.damochaohe.product.mapper.ProductCategoryMapper;
import com.damochaohe.product.mapper.ProductSpuMapper;
import com.damochaohe.product.service.AdminProductManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台商品管理实现。
 */
@Service
@RequiredArgsConstructor
public class AdminProductManageServiceImpl implements AdminProductManageService {

    private final ProductCategoryMapper productCategoryMapper;
    private final ProductSpuMapper productSpuMapper;

    @Override
    public List<AdminCategoryResponse> listCategories() {
        return productCategoryMapper.selectList(new LambdaQueryWrapper<ProductCategoryEntity>()
                        .orderByAsc(ProductCategoryEntity::getSortNo)
                        .orderByAsc(ProductCategoryEntity::getId))
                .stream()
                .map(item -> AdminCategoryResponse.builder()
                        .id(item.getId())
                        .parentId(item.getParentId())
                        .categoryName(item.getCategoryName())
                        .categoryLevel(item.getCategoryLevel())
                        .status(item.getStatus())
                        .sortNo(item.getSortNo())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void saveCategory(AdminCategorySaveRequest request) {
        ProductCategoryEntity entity = request.getId() == null ? new ProductCategoryEntity() : productCategoryMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("商品分类不存在");
        }
        entity.setParentId(request.getParentId());
        entity.setCategoryName(request.getCategoryName());
        entity.setCategoryLevel(request.getCategoryLevel());
        entity.setStatus(request.getStatus());
        entity.setSortNo(request.getSortNo());
        if (request.getId() == null) {
            productCategoryMapper.insert(entity);
        } else {
            productCategoryMapper.updateById(entity);
        }
    }

    @Override
    public List<AdminProductResponse> listProducts() {
        return productSpuMapper.selectList(new LambdaQueryWrapper<ProductSpuEntity>()
                        .orderByAsc(ProductSpuEntity::getSortNo)
                        .orderByDesc(ProductSpuEntity::getId))
                .stream()
                .map(item -> AdminProductResponse.builder()
                        .id(item.getId())
                        .categoryId(item.getCategoryId())
                        .productName(item.getProductName())
                        .productType(item.getProductType())
                        .coverUrl(item.getCoverUrl())
                        .costPrice(item.getCostPrice())
                        .marketPrice(item.getMarketPrice())
                        .status(item.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void saveProduct(AdminProductSaveRequest request) {
        ProductSpuEntity entity = request.getId() == null ? new ProductSpuEntity() : productSpuMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("商品不存在");
        }
        entity.setCategoryId(request.getCategoryId());
        entity.setProductName(request.getProductName());
        entity.setProductType(request.getProductType());
        entity.setCoverUrl(request.getCoverUrl());
        entity.setCostPrice(request.getCostPrice());
        entity.setMarketPrice(request.getMarketPrice());
        entity.setStatus(request.getStatus());
        entity.setSortNo(request.getSortNo());
        if (request.getId() == null) {
            productSpuMapper.insert(entity);
        } else {
            productSpuMapper.updateById(entity);
        }
    }
}
