package com.damochaohe.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damochaohe.infra.entity.SysDictEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统字典 Mapper。
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDictEntity> {
}
