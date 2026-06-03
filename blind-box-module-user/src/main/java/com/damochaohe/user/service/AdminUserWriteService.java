package com.damochaohe.user.service;

/**
 * 后台用户写服务。
 */
public interface AdminUserWriteService {

    /**
     * 修改用户状态。
     *
     * @param userId 用户 ID
     * @param status 目标状态
     */
    void updateUserStatus(Long userId, Integer status);
}
