package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "后台优惠券核销记录响应")
public class AdminCouponConsumeRecordResponse {
    @Schema(description = "核销记录 ID")
    private Long id;
    @Schema(description = "用户优惠券 ID")
    private Long userCouponId;
    @Schema(description = "用户 ID")
    private Long userId;
    @Schema(description = "优惠券名称")
    private String couponName;
    @Schema(description = "业务单号")
    private String bizNo;
    @Schema(description = "核销金额")
    private BigDecimal discountAmount;
    @Schema(description = "核销时间")
    private LocalDateTime createdAt;
}
