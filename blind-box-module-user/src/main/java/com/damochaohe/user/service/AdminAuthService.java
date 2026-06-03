package com.damochaohe.user.service;

import com.damochaohe.user.dto.AdminLoginRequest;
import com.damochaohe.user.dto.AdminLoginResponse;
import com.damochaohe.user.dto.AdminPermissionResponse;

/**
 * 后台管理员认证服务。
 */
public interface AdminAuthService {

    /**
     * 管理员登录。
     *
     * @param request 登录请求
     * @return 登录结果
     */
    AdminLoginResponse login(AdminLoginRequest request);

    /**
     * 查询当前管理员权限集合。
     *
     * @return 权限数据
     */
    AdminPermissionResponse getPermissionInfo();
}
