package com.damochaohe.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "后台首页中奖轮播配置项")
public class AdminWinnerTickerConfigResponse {
    @Schema(description = "配置 ID")
    private Long id;
    @Schema(description = "轮播文案")
    private String content;
    @Schema(description = "状态")
    private Integer status;
    @Schema(description = "排序号")
    private Integer sortNo;
}
