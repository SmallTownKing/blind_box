package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 奖池基础配置实体。
 */
@Data
@TableName("play_pool")
public class PlayPoolEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String poolName;

    private String poolType;

    private Long categoryId;

    private Integer status;

    private Integer sortNo;

    private String bannerLandingPage;

    private String drawModeConfig;

    private String payModeConfig;

    private Integer trialEnabled;

    private Integer animationEnabled;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
