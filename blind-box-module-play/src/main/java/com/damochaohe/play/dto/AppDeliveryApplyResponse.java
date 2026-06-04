package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 用户提货申请响应。
 */
@Data
@Builder
@Schema(description = "用户提货申请响应")
public class AppDeliveryApplyResponse {

    @Schema(description = "提货单号")
    private String deliveryNo;

    @Schema(description = "仓库单号")
    private String warehouseNo;

    @Schema(description = "提货状态")
    private String deliveryStatus;
}
