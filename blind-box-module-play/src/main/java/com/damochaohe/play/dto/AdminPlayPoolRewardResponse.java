package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 奖池奖品项响应。
 */
@Data
@Builder
@Schema(description = "奖池奖品项响应")
public class AdminPlayPoolRewardResponse {

    @Schema(description = "奖品项 ID")
    private Long id;

    @Schema(description = "奖池 ID")
    private Long poolId;

    @Schema(description = "商品 ID")
    private Long productId;

    @Schema(description = "奖品名称")
    private String rewardName;

    @Schema(description = "奖项层级")
    private String rewardLevel;

    @Schema(description = "中奖概率")
    private BigDecimal probability;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "状态")
    private Integer status;
}
