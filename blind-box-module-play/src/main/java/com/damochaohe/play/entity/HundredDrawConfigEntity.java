package com.damochaohe.play.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 百连抽页面配置实体。
 */
@Data
@TableName("hundred_draw_config")
public class HundredDrawConfigEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long poolId;

    private String pageTitle;

    private String pageSubtitle;

    private String bannerUrl;

    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
