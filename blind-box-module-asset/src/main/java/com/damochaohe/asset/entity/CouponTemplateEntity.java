package com.damochaohe.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("coupon_template")
public class CouponTemplateEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String couponName;
    private String couponType;
    private BigDecimal discountAmount;
    private BigDecimal thresholdAmount;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
