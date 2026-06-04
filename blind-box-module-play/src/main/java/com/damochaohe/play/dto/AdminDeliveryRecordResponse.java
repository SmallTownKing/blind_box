package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 后台提货记录响应。
 */
@Data
@Builder
@Schema(description = "后台提货记录响应")
public class AdminDeliveryRecordResponse {

    @Schema(description = "提货单号")
    private String deliveryNo;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "奖品名称")
    private String rewardName;

    @Schema(description = "提货状态")
    private String deliveryStatus;

    @Schema(description = "物流公司")
    private String logisticsCompany;

    @Schema(description = "物流单号")
    private String logisticsNo;

    @Schema(description = "后台备注")
    private String adminRemark;
}
