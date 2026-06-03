package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 一番赏活动保存请求。
 */
@Data
@Schema(description = "一番赏活动保存请求")
public class AdminKujiActivitySaveRequest {

    @Schema(description = "活动 ID，新增时为空")
    private Long id;

    @NotBlank(message = "活动名称不能为空")
    @Schema(description = "活动名称")
    private String activityName;

    @NotNull(message = "分类 ID 不能为空")
    @Schema(description = "关联分类 ID")
    private Long categoryId;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态：1启用 0停用")
    private Integer status;

    @NotNull(message = "锁箱开关不能为空")
    @Schema(description = "锁箱开关：1开启 0关闭")
    private Integer lockBoxEnabled;

    @Schema(description = "整箱总库存")
    private Integer boxTotalStock = 0;

    @Schema(description = "整箱剩余库存")
    private Integer boxRemainStock = 0;

    @Schema(description = "每用户购买次数限制，0表示不限制")
    private Integer purchaseLimit = 0;

    @Schema(description = "排序号")
    private Integer sortNo = 0;
}
