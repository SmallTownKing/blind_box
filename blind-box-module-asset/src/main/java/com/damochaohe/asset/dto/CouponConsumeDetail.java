package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "优惠券核销明细")
public class CouponConsumeDetail {

    @Schema(description = "用户优惠券 ID")
    private Long userCouponId;

    @Schema(description = "优惠券名称")
    private String couponName;

    @Schema(description = "核销金额")
    private BigDecimal discountAmount;
}
