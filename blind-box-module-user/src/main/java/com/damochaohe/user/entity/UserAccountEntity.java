package com.damochaohe.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户基础实体。
 */
@Data
@TableName("user_account")
public class UserAccountEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String userNo;

    private String nickname;

    private String avatar;

    private String mobile;

    private String email;

    private LocalDate birthday;

    private String passwordHash;

    private Integer status;

    private String registerSource;

    private Integer currentMemberLevel;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
