package com.damochaohe.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_coupon")
public class UserCouponEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long templateId;
    private String couponStatus;
    private String couponCode;
    private LocalDateTime expireTime;
    private LocalDateTime usedTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
