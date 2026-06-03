package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 福袋玩法规则响应。
 */
@Data
@Builder
@Schema(description = "福袋玩法规则响应")
public class AdminFukubukuroRuleResponse {

    @Schema(description = "规则 ID")
    private Long id;

    @Schema(description = "奖池 ID")
    private Long poolId;

    @Schema(description = "稀有度类型")
    private String rarityType;

    @Schema(description = "未出提示文案")
    private String noHitTip;

    @Schema(description = "自动选中价格上限")
    private BigDecimal autoSelectPriceLimit;

    @Schema(description = "状态")
    private Integer status;
}
