package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户提货申请请求。
 */
@Data
@Schema(description = "用户提货申请请求")
public class AppDeliveryApplyRequest {

    @NotBlank(message = "仓库单号不能为空")
    @Schema(description = "仓库单号")
    private String warehouseNo;
}
