package com.damochaohe.infra.service.impl;

import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.infra.dto.SysDictSaveRequest;
import com.damochaohe.infra.entity.SysDictEntity;
import com.damochaohe.infra.mapper.SysDictMapper;
import com.damochaohe.infra.service.SysDictWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统字典写服务实现。
 */
@Service
@RequiredArgsConstructor
public class SysDictWriteServiceImpl implements SysDictWriteService {

    private final SysDictMapper sysDictMapper;

    @Override
    public void save(SysDictSaveRequest request) {
        SysDictEntity entity = request.getId() == null ? new SysDictEntity() : sysDictMapper.selectById(request.getId());
        if (request.getId() != null && entity == null) {
            throw new BusinessException("字典项不存在");
        }
        entity.setDictType(request.getDictType());
        entity.setLabel(request.getLabel());
        entity.setValue(request.getValue());
        entity.setStatus(request.getStatus());
        entity.setSortNo(request.getSortNo());
        entity.setRemark(request.getRemark());
        if (request.getId() == null) {
            sysDictMapper.insert(entity);
        } else {
            sysDictMapper.updateById(entity);
        }
    }
}
