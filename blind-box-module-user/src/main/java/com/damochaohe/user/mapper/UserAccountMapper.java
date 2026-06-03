package com.damochaohe.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damochaohe.user.entity.UserAccountEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户基础 Mapper。
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccountEntity> {
}
