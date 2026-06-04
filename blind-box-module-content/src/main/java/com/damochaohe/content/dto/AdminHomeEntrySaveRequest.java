package com.damochaohe.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "首页金刚区保存请求")
public class AdminHomeEntrySaveRequest {
    @Schema(description = "配置 ID，为空时新增")
    private Long id;
    @NotBlank(message = "入口名称不能为空")
    private String name;
    @NotBlank(message = "图标地址不能为空")
    private String iconUrl;
    @NotBlank(message = "跳转路径不能为空")
    private String targetPath;
    @NotNull(message = "状态不能为空")
    private Integer status;
    @NotNull(message = "排序号不能为空")
    private Integer sortNo;
}
