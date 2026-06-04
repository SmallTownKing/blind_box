package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("draw_audit_log")
public class DrawAuditLogEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String tradeNo;
    private Long userId;
    private Long poolId;
    private String probabilitySnapshot;
    private Integer targetRewardHit;
    private Integer finalRewardHit;
    private String stockChangeSnapshot;
    private LocalDateTime createdAt;
}
