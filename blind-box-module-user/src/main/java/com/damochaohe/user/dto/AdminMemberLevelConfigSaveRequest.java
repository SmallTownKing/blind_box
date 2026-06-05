package com.damochaohe.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "后台会员等级配置保存请求")
public class AdminMemberLevelConfigSaveRequest {
    private Long id;
    @NotNull(message = "等级编号不能为空")
    private Integer levelNo;
    @NotBlank(message = "等级名称不能为空")
    private String levelName;
    @NotNull(message = "消费门槛不能为空")
    private BigDecimal consumeThreshold;
    @NotNull(message = "成长值门槛不能为空")
    private BigDecimal growthThreshold;
    private String rewardConfig;
    @NotNull(message = "V10保护开关不能为空")
    private Integer v10ProtectEnabled;
    private BigDecimal v10ProtectThreshold;
    @NotNull(message = "状态不能为空")
    private Integer status;
    @NotNull(message = "排序号不能为空")
    private Integer sortNo;
}
