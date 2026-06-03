package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 奖池基础配置保存请求。
 */
@Data
@Schema(description = "奖池基础配置保存请求")
public class AdminPlayPoolSaveRequest {

    @Schema(description = "奖池 ID，新增时为空")
    private Long id;

    @NotBlank(message = "奖池名称不能为空")
    @Schema(description = "奖池名称")
    private String poolName;

    @NotBlank(message = "奖池类型不能为空")
    @Schema(description = "奖池类型：FUKUBUKURO/KUJI/DEMON")
    private String poolType;

    @NotNull(message = "关联分类不能为空")
    @Schema(description = "关联分类 ID")
    private Long categoryId;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0停用")
    private Integer status;

    @Schema(description = "排序号")
    private Integer sortNo = 0;

    @Schema(description = "Banner 关联落地页路径")
    private String bannerLandingPage;

    @Schema(description = "抽选方式配置，多个用英文逗号分隔，如 SINGLE,FIVE,TEN")
    private String drawModeConfig;

    @Schema(description = "支付方式配置，多个用英文逗号分隔，如 BALANCE,GOLD,MAGIC,COUPON")
    private String payModeConfig;

    @Schema(description = "是否开启试玩：1是 0否")
    private Integer trialEnabled = 0;

    @Schema(description = "是否开启开箱动效：1是 0否")
    private Integer animationEnabled = 1;
}
