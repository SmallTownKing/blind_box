package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 抽赏交易记录响应。
 */
@Data
@Builder
@Schema(description = "抽赏交易记录响应")
public class AppTradeRecordResponse {

    @Schema(description = "交易单号")
    private String tradeNo;

    @Schema(description = "支付方式")
    private String payModes;

    @Schema(description = "支付金额")
    private BigDecimal totalCost;

    @Schema(description = "交易状态")
    private String tradeStatus;
}
