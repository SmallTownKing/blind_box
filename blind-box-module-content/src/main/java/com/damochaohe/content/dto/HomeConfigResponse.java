package com.damochaohe.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 首页配置聚合响应。
 */
@Data
@Builder
@Schema(description = "首页配置响应")
public class HomeConfigResponse {

    @Schema(description = "开屏广告标题")
    private String splashTitle;

    @Schema(description = "首页 Banner 列表")
    private List<String> banners;

    @Schema(description = "金刚区入口名称列表")
    private List<String> entries;
}
