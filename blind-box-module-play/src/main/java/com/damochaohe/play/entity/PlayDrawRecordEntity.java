package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("play_draw_record")
public class PlayDrawRecordEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String tradeNo;
    private Long userId;
    private Long poolId;
    private Long rewardId;
    private String rewardName;
    private String rewardLevel;
    private Integer drawCount;
    private String payModes;
    private BigDecimal totalCost;
    private LocalDateTime createdAt;
}
