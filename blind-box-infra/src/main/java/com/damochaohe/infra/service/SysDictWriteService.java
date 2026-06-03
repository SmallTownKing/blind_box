package com.damochaohe.infra.service;

import com.damochaohe.infra.dto.SysDictSaveRequest;

/**
 * 系统字典写服务。
 */
public interface SysDictWriteService {

    /**
     * 保存系统字典项。
     *
     * @param request 请求参数
     */
    void save(SysDictSaveRequest request);
}
