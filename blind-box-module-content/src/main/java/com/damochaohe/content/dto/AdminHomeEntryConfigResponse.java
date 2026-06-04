package com.damochaohe.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "后台首页金刚区配置项")
public class AdminHomeEntryConfigResponse {
    @Schema(description = "配置 ID")
    private Long id;
    @Schema(description = "入口名称")
    private String name;
    @Schema(description = "图标地址")
    private String iconUrl;
    @Schema(description = "跳转路径")
    private String targetPath;
    @Schema(description = "状态")
    private Integer status;
    @Schema(description = "排序号")
    private Integer sortNo;
}
