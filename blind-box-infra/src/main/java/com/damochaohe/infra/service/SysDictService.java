package com.damochaohe.infra.service;

import com.damochaohe.common.dto.SysDictItemResponse;

import java.util.List;

/**
 * 系统字典服务。
 */
public interface SysDictService {

    /**
     * 根据字典类型查询字典项。
     *
     * @param dictType 字典类型
     * @return 字典项列表
     */
    List<SysDictItemResponse> listByType(String dictType);
}
