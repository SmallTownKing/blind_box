package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "后台发放优惠券请求")
public class AdminCouponIssueRequest {
    @NotNull(message = "用户 ID 不能为空")
    private Long userId;
    @NotNull(message = "模板 ID 不能为空")
    private Long templateId;
    @Schema(description = "过期小时数，默认 24 * 30")
    private Integer expireHours;
}
