package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 一番赏活动实体。
 */
@Data
@TableName("kuji_activity")
public class KujiActivityEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String activityName;

    private Long categoryId;

    private Integer status;

    private Integer lockBoxEnabled;

    private Integer boxTotalStock;

    private Integer boxRemainStock;

    private Integer purchaseLimit;

    private Integer sortNo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
