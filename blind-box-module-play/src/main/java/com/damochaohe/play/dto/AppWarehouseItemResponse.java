package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 模拟入仓记录。
 */
@Data
@Builder
@Schema(description = "模拟入仓记录")
public class AppWarehouseItemResponse {

    @Schema(description = "仓库记录号")
    private String warehouseNo;

    @Schema(description = "奖品名称")
    private String rewardName;

    @Schema(description = "奖品等级")
    private String rewardLevel;

    @Schema(description = "状态")
    private String status;
}
