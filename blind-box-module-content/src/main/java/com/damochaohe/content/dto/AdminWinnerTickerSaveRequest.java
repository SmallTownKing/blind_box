package com.damochaohe.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "首页中奖轮播保存请求")
public class AdminWinnerTickerSaveRequest {
    @Schema(description = "配置 ID，为空时新增")
    private Long id;
    @NotBlank(message = "轮播文案不能为空")
    private String content;
    @NotNull(message = "状态不能为空")
    private Integer status;
    @NotNull(message = "排序号不能为空")
    private Integer sortNo;
}
