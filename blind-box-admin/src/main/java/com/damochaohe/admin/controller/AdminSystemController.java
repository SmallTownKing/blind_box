package com.damochaohe.admin.controller;

import com.damochaohe.common.dto.SysDictItemResponse;
import com.damochaohe.common.response.ApiResponse;
import com.damochaohe.infra.dto.SysDictSaveRequest;
import com.damochaohe.infra.service.SysDictService;
import com.damochaohe.infra.service.SysDictWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台系统配置接口。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/system")
@Tag(name = "ADMIN-系统配置")
public class AdminSystemController {

    private final SysDictService sysDictService;
    private final SysDictWriteService sysDictWriteService;

    @GetMapping("/dict/items")
    @Operation(summary = "根据字典类型查询字典项")
    public ApiResponse<List<SysDictItemResponse>> dictItems(
            @Parameter(description = "字典类型，例如 user_status / content_status")
            @RequestParam String dictType) {
        return ApiResponse.success(sysDictService.listByType(dictType));
    }

    @PostMapping("/dict/save")
    @Operation(summary = "新增或修改系统字典项")
    public ApiResponse<Void> saveDict(@Valid @RequestBody SysDictSaveRequest request) {
        sysDictWriteService.save(request);
        return ApiResponse.success(null);
    }
}
