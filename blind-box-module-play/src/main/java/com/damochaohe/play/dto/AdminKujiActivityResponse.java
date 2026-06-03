package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 一番赏活动响应。
 */
@Data
@Builder
@Schema(description = "一番赏活动响应")
public class AdminKujiActivityResponse {

    @Schema(description = "活动 ID")
    private Long id;

    @Schema(description = "活动名称")
    private String activityName;

    @Schema(description = "分类 ID")
    private Long categoryId;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "锁箱开关")
    private Integer lockBoxEnabled;

    @Schema(description = "整箱总库存")
    private Integer boxTotalStock;

    @Schema(description = "整箱剩余库存")
    private Integer boxRemainStock;

    @Schema(description = "每用户购买次数限制")
    private Integer purchaseLimit;
}
