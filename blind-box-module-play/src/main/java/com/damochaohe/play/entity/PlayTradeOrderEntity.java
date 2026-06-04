package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("play_trade_order")
public class PlayTradeOrderEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String tradeNo;
    private Long userId;
    private Long poolId;
    private String payModes;
    private BigDecimal totalCost;
    private String tradeStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
