package com.damochaohe.asset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "后台优惠券模板保存请求")
public class AdminCouponTemplateSaveRequest {
    @Schema(description = "模板 ID，为空时新增")
    private Long id;
    @NotBlank(message = "优惠券名称不能为空")
    private String couponName;
    @NotBlank(message = "优惠券类型不能为空")
    private String couponType;
    @NotNull(message = "优惠金额不能为空")
    private BigDecimal discountAmount;
    @NotNull(message = "使用门槛不能为空")
    private BigDecimal thresholdAmount;
    @NotNull(message = "状态不能为空")
    private Integer status;
}
