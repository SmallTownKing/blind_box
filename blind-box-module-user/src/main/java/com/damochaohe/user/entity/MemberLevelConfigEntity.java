package com.damochaohe.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("member_level_config")
public class MemberLevelConfigEntity {
    @TableId(type = IdType.AUTO)
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
