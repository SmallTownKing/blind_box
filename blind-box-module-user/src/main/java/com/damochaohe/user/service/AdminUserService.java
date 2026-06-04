package com.damochaohe.user.service;

import com.damochaohe.common.model.PageResponse;
import com.damochaohe.user.dto.AdminUserDetailResponse;
import com.damochaohe.user.dto.AdminUserPageResponse;
import com.damochaohe.user.dto.AdminUserQuery;

/**
 * 后台用户管理服务。
 */
public interface AdminUserService {

    /**
     * 分页查询用户列表。
     *
     * @param query 查询参数
     * @return 分页结果
     */
    PageResponse<AdminUserPageResponse> pageUsers(AdminUserQuery query);

    /**
     * 查询用户详情。
     *
     * @param userId 用户 ID
     * @return 用户详情
     */
    AdminUserDetailResponse getUserDetail(Long userId);
}
