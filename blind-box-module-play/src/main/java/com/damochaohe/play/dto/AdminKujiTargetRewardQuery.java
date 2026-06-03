package com.damochaohe.play.dto;

import com.damochaohe.common.model.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 指定中奖配置分页查询参数。
 */
@Data
@Schema(description = "指定中奖配置分页查询参数")
public class AdminKujiTargetRewardQuery extends BasePageQuery {

    @Schema(description = "活动 ID")
    private Long activityId;
}
