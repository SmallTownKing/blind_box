package com.damochaohe.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("admin_user_role")
public class AdminUserRoleEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long adminUserId;
    private Long roleId;
    private LocalDateTime createdAt;
}
