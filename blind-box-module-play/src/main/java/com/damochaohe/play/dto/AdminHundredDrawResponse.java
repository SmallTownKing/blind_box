package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 百连抽页面配置响应。
 */
@Data
@Builder
@Schema(description = "百连抽页面配置响应")
public class AdminHundredDrawResponse {

    @Schema(description = "配置 ID")
    private Long id;

    @Schema(description = "奖池 ID")
    private Long poolId;

    @Schema(description = "页面标题")
    private String pageTitle;

    @Schema(description = "页面副标题")
    private String pageSubtitle;

    @Schema(description = "页面 Banner 图")
    private String bannerUrl;

    @Schema(description = "状态")
    private Integer status;
}
