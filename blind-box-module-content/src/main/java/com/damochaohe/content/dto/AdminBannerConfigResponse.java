package com.damochaohe.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 后台 Banner 配置项。
 */
@Data
@Builder
@Schema(description = "后台 Banner 配置项")
public class AdminBannerConfigResponse {

    @Schema(description = "配置 ID")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "图片地址")
    private String imageUrl;

    @Schema(description = "跳转路径")
    private String targetPath;

    @Schema(description = "启用状态")
    private Integer status;
}
