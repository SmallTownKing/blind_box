package com.damochaohe.infra.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统字典实体。
 */
@Data
@TableName("sys_dict")
public class SysDictEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String dictType;

    private String label;

    private String value;

    private Integer status;

    private Integer sortNo;

    private String remark;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
