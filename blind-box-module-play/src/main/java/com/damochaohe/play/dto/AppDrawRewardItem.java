package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 抽赏结果项。
 */
@Data
@Builder
@Schema(description = "抽赏结果项")
public class AppDrawRewardItem {

    @Schema(description = "奖品项 ID")
    private Long rewardId;

    @Schema(description = "奖品名称")
    private String rewardName;

    @Schema(description = "奖品等级")
    private String rewardLevel;

    @Schema(description = "来源奖池 ID")
    private Long poolId;
}
