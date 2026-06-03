package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 一番赏锁箱记录实体。
 */
@Data
@TableName("kuji_lock_record")
public class KujiLockRecordEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long activityId;

    private Long userId;

    private Integer lockStatus;

    private LocalDateTime lockExpireTime;

    private String releaseReason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
