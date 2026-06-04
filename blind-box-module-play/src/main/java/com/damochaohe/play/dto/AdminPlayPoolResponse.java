package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 奖池基础配置响应。
 */
@Data
@Builder
@Schema(description = "奖池基础配置响应")
public class AdminPlayPoolResponse {

    @Schema(description = "奖池 ID")
    private Long id;

    @Schema(description = "奖池名称")
    private String poolName;

    @Schema(description = "奖池类型")
    private String poolType;

    @Schema(description = "关联分类 ID")
    private Long categoryId;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "Banner 关联落地页")
    private String bannerLandingPage;

    @Schema(description = "抽选方式配置")
    private String drawModeConfig;

    @Schema(description = "支付方式配置")
    private String payModeConfig;

    @Schema(description = "支付方式展示开关配置")
    private String payModeSwitchConfig;

    @Schema(description = "抽选按钮配置")
    private String drawButtonConfig;

    @Schema(description = "激情模式配置")
    private String passionModeConfig;

    @Schema(description = "是否开启未出超稀有款提示")
    private Integer noHitRareTipEnabled;

    @Schema(description = "指定中奖配置")
    private String guaranteeConfig;

    @Schema(description = "试玩开关")
    private Integer trialEnabled;

    @Schema(description = "开箱动效开关")
    private Integer animationEnabled;
}
