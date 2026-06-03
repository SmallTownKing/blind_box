package com.damochaohe.infra.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.common.dto.SysDictItemResponse;
import com.damochaohe.infra.entity.SysDictEntity;
import com.damochaohe.infra.mapper.SysDictMapper;
import com.damochaohe.infra.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统字典服务实现。
 *
 * <p>当前基于 [`sys_dict`](sql/init-blind-box-admin.sql) 表查询字典数据。</p>
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl implements SysDictService {

    private final SysDictMapper sysDictMapper;

    @Override
    public List<SysDictItemResponse> listByType(String dictType) {
        return sysDictMapper.selectList(new LambdaQueryWrapper<SysDictEntity>()
                        .eq(SysDictEntity::getDictType, dictType)
                        .eq(SysDictEntity::getStatus, 1)
                        .orderByAsc(SysDictEntity::getSortNo)
                        .orderByAsc(SysDictEntity::getId))
                .stream()
                .map(item -> SysDictItemResponse.builder()
                        .dictType(item.getDictType())
                        .label(item.getLabel())
                        .value(item.getValue())
                        .sortNo(item.getSortNo())
                        .build())
                .collect(Collectors.toList());
    }
}
