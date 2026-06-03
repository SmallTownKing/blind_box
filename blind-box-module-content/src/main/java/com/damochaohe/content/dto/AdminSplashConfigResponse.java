package com.damochaohe.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 后台开屏广告配置项。
 */
@Data
@Builder
@Schema(description = "后台开屏广告配置项")
public class AdminSplashConfigResponse {

    @Schema(description = "配置 ID")
    private Long id;

    @Schema(description = "广告标题")
    private String title;

    @Schema(description = "图片地址")
    private String imageUrl;

    @Schema(description = "内部跳转页面")
    private String targetPath;

    @Schema(description = "启用状态")
    private Integer status;
}
