package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "支付拆账明细")
public class PaymentSplitDetail {

    @Schema(description = "支付资产类型")
    private String payType;

    @Schema(description = "扣减金额")
    private BigDecimal amount;
}
