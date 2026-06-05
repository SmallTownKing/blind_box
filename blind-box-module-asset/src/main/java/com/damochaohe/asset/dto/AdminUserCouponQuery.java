package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "后台用户优惠券查询参数")
public class AdminUserCouponQuery {
    @Schema(description = "用户 ID，可为空")
    private Long userId;
    @Schema(description = "优惠券状态 UNUSED/USED/EXPIRED，可为空")
    private String couponStatus;
}
