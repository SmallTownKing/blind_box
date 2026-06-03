package com.damochaohe.product.service;

import com.damochaohe.product.dto.AdminCategoryResponse;
import com.damochaohe.product.dto.AdminCategorySaveRequest;
import com.damochaohe.product.dto.AdminProductResponse;
import com.damochaohe.product.dto.AdminProductSaveRequest;

import java.util.List;

/**
 * 后台商品管理服务。
 */
public interface AdminProductManageService {

    List<AdminCategoryResponse> listCategories();

    void saveCategory(AdminCategorySaveRequest request);

    List<AdminProductResponse> listProducts();

    void saveProduct(AdminProductSaveRequest request);
}
