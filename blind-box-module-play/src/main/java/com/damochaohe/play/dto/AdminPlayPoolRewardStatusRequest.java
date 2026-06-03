package com.damochaohe.play.dto;

import com.damochaohe.common.model.StatusUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 奖池奖品项状态修改请求。
 */
@Schema(description = "奖池奖品项状态修改请求")
public class AdminPlayPoolRewardStatusRequest extends StatusUpdateRequest {
}
