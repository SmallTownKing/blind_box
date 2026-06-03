package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 一番赏锁箱响应。
 */
@Data
@Builder
@Schema(description = "一番赏锁箱响应")
public class AdminKujiLockResponse {

    @Schema(description = "锁箱记录 ID")
    private Long id;

    @Schema(description = "活动 ID")
    private Long activityId;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "锁定状态")
    private Integer lockStatus;

    @Schema(description = "锁定失效时间")
    private LocalDateTime lockExpireTime;

    @Schema(description = "释放原因")
    private String releaseReason;
}
