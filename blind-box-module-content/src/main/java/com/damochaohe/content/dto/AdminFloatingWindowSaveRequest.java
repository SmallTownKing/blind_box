package com.damochaohe.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "首页悬浮窗保存请求")
public class AdminFloatingWindowSaveRequest {
    @Schema(description = "配置 ID，为空时新增")
    private Long id;
    private String title;
    @NotBlank(message = "图片地址不能为空")
    private String imageUrl;
    @NotBlank(message = "跳转路径不能为空")
    private String targetPath;
    @NotNull(message = "状态不能为空")
    private Integer status;
    @NotNull(message = "排序号不能为空")
    private Integer sortNo;
}
