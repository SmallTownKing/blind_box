package com.damochaohe.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damochaohe.user.entity.AdminUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台管理员 Mapper。
 */
@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUserEntity> {
}
