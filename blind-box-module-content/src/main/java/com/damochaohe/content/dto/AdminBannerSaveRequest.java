package com.damochaohe.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Banner 保存请求。
 */
@Data
@Schema(description = "Banner 保存请求")
public class AdminBannerSaveRequest {

    @Schema(description = "配置 ID，新增时可为空")
    private Long id;

    @NotBlank(message = "标题不能为空")
    @Schema(description = "标题")
    private String title;

    @NotBlank(message = "图片地址不能为空")
    @Schema(description = "图片地址")
    private String imageUrl;

    @Schema(description = "跳转路径")
    private String targetPath;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0停用")
    private Integer status;

    @Schema(description = "排序号")
    private Integer sortNo = 0;
}
