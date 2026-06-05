package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "后台会员等级配置响应")
public class AdminMemberLevelConfigResponse {
    private Long id;
    private Integer levelNo;
    private String levelName;
    private BigDecimal consumeThreshold;
    private BigDecimal growthThreshold;
    private String rewardConfig;
    private Integer v10ProtectEnabled;
    private BigDecimal v10ProtectThreshold;
    private Integer status;
    private Integer sortNo;
}
