package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "后台提货状态更新请求")
public class AdminDeliveryUpdateRequest {

    @NotBlank(message = "提货单号不能为空")
    @Schema(description = "提货单号")
    private String deliveryNo;

    @NotBlank(message = "目标状态不能为空")
    @Schema(description = "目标状态：SHIPPED/COMPLETED/CANCELLED")
    private String deliveryStatus;

    @Schema(description = "物流公司")
    private String logisticsCompany;

    @Schema(description = "物流单号")
    private String logisticsNo;

    @Schema(description = "后台备注")
    private String adminRemark;
}
