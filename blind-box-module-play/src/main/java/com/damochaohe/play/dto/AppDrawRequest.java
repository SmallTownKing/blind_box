package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户端抽赏请求。
 */
@Data
@Schema(description = "用户端抽赏请求")
public class AppDrawRequest {

    @NotNull(message = "奖池 ID 不能为空")
    @Schema(description = "奖池 ID")
    private Long poolId;

    @NotNull(message = "抽取次数不能为空")
    @Min(value = 1, message = "抽取次数至少为 1")
    @Schema(description = "抽取次数，支持 1 / 5 / 10", example = "1")
    private Integer drawCount;

    @Schema(description = "支付方式组合描述", example = "BALANCE+MAGIC")
    private String payModes;
}
