package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 商用版抽赏请求。
 */
@Data
@Schema(description = "商用版抽赏请求")
public class CommercialDrawRequest {

    @NotNull(message = "奖池 ID 不能为空")
    @Schema(description = "奖池 ID")
    private Long poolId;

    @NotNull(message = "抽取次数不能为空")
    @Min(value = 1, message = "抽取次数至少为 1")
    @Schema(description = "抽取次数")
    private Integer drawCount;

    @Schema(description = "支付方式优先级，例如 BALANCE,GOLD,MAGIC")
    private List<String> payTypes;

    @Schema(description = "使用的用户优惠券 ID 列表")
    private List<Long> userCouponIds;
}
