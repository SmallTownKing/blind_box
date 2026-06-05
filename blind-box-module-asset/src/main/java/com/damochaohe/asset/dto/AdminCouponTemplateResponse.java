package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "后台优惠券模板响应")
public class AdminCouponTemplateResponse {
    @Schema(description = "模板 ID")
    private Long id;
    @Schema(description = "优惠券名称")
    private String couponName;
    @Schema(description = "优惠券类型")
    private String couponType;
    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;
    @Schema(description = "使用门槛")
    private BigDecimal thresholdAmount;
    @Schema(description = "状态")
    private Integer status;
}
