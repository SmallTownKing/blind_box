package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 后台提货查询参数。
 */
@Data
@Schema(description = "后台提货查询参数")
public class AdminDeliveryQuery {

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "提货状态")
    private String deliveryStatus;
}
