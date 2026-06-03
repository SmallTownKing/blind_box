package com.damochaohe.play.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 一番赏锁箱配置请求。
 */
@Data
@Schema(description = "一番赏锁箱配置请求")
public class AdminKujiLockSaveRequest {

    @Schema(description = "锁箱记录 ID，新增时为空")
    private Long id;

    @NotNull(message = "活动 ID 不能为空")
    @Schema(description = "活动 ID")
    private Long activityId;

    @NotNull(message = "用户 ID 不能为空")
    @Schema(description = "用户 ID")
    private Long userId;

    @NotNull(message = "锁定状态不能为空")
    @Schema(description = "锁定状态：1锁定 0释放")
    private Integer lockStatus;

    @Schema(description = "锁定失效时间")
    private LocalDateTime lockExpireTime;

    @Schema(description = "释放原因")
    private String releaseReason;
}
