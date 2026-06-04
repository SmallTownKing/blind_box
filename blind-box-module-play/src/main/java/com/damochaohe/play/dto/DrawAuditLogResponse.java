package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 抽奖审计日志响应。
 */
@Data
@Builder
@Schema(description = "抽奖审计日志响应")
public class DrawAuditLogResponse {

    @Schema(description = "交易单号")
    private String tradeNo;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "奖池 ID")
    private Long poolId;

    @Schema(description = "概率快照")
    private String probabilitySnapshot;

    @Schema(description = "是否命中指定中奖")
    private Integer targetRewardHit;

    @Schema(description = "是否命中最终赏")
    private Integer finalRewardHit;

    @Schema(description = "库存变化说明")
    private String stockChangeSnapshot;
}
