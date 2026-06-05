package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "后台用户优惠券响应")
public class AdminUserCouponResponse {
    @Schema(description = "用户优惠券 ID")
    private Long id;
    @Schema(description = "用户 ID")
    private Long userId;
    @Schema(description = "模板 ID")
    private Long templateId;
    @Schema(description = "优惠券名称")
    private String couponName;
    @Schema(description = "优惠券状态")
    private String couponStatus;
    @Schema(description = "优惠券码")
    private String couponCode;
    @Schema(description = "过期时间")
    private LocalDateTime expireTime;
    @Schema(description = "使用时间")
    private LocalDateTime usedTime;
}
