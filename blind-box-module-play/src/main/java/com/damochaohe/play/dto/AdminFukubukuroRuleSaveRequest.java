package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 福袋玩法规则保存请求。
 */
@Data
@Schema(description = "福袋玩法规则保存请求")
public class AdminFukubukuroRuleSaveRequest {

    @Schema(description = "规则 ID，新增时为空")
    private Long id;

    @NotNull(message = "奖池 ID 不能为空")
    @Schema(description = "奖池 ID")
    private Long poolId;

    @NotBlank(message = "稀有度类型不能为空")
    @Schema(description = "稀有度类型")
    private String rarityType;

    @Schema(description = "未出提示文案")
    private String noHitTip;

    @Schema(description = "自动选中价格上限")
    private BigDecimal autoSelectPriceLimit;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0停用")
    private Integer status;

    @Schema(description = "排序号")
    private Integer sortNo = 0;
}
