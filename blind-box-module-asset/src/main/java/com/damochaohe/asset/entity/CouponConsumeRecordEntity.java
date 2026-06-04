package com.damochaohe.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("coupon_consume_record")
public class CouponConsumeRecordEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userCouponId;
    private Long userId;
    private String bizNo;
    private BigDecimal discountAmount;
    private LocalDateTime createdAt;
}
