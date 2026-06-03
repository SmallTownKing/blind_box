package com.damochaohe.admin.controller;

import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.product.dto.AdminCategoryResponse;
import com.damochaohe.product.dto.AdminCategorySaveRequest;
import com.damochaohe.product.dto.AdminProductResponse;
import com.damochaohe.product.dto.AdminProductSaveRequest;
import com.damochaohe.product.service.AdminProductManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台商品管理接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/product")
@Tag(name = "ADMIN-商品管理")
public class AdminProductController {

    private final AdminProductManageService adminProductManageService;

    @GetMapping("/categories")
    @Operation(summary = "查询商品分类列表")
    public ApiResponse<List<AdminCategoryResponse>> categories() {
        return ApiResponse.success(adminProductManageService.listCategories());
    }

    @PostMapping("/category/save")
    @Operation(summary = "新增或修改商品分类")
    public ApiResponse<Void> saveCategory(@Valid @RequestBody AdminCategorySaveRequest request) {
        adminProductManageService.saveCategory(request);
        return ApiResponse.success(null);
    }

    @GetMapping("/spus")
    @Operation(summary = "查询商品列表")
    public ApiResponse<List<AdminProductResponse>> products() {
        return ApiResponse.success(adminProductManageService.listProducts());
    }

    @PostMapping("/spu/save")
    @Operation(summary = "新增或修改商品")
    public ApiResponse<Void> saveProduct(@Valid @RequestBody AdminProductSaveRequest request) {
        adminProductManageService.saveProduct(request);
        return ApiResponse.success(null);
    }
}
