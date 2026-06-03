package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 百连抽页面配置保存请求。
 */
@Data
@Schema(description = "百连抽页面配置保存请求")
public class AdminHundredDrawSaveRequest {

    @Schema(description = "配置 ID，新增时为空")
    private Long id;

    @NotNull(message = "奖池 ID 不能为空")
    @Schema(description = "奖池 ID")
    private Long poolId;

    @NotBlank(message = "页面标题不能为空")
    @Schema(description = "页面标题")
    private String pageTitle;

    @Schema(description = "页面副标题")
    private String pageSubtitle;

    @Schema(description = "页面 Banner 图")
    private String bannerUrl;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0停用")
    private Integer status;
}
