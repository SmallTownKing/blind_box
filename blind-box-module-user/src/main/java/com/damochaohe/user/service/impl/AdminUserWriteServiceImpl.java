package com.damochaohe.user.service.impl;

import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.user.entity.UserAccountEntity;
import com.damochaohe.user.mapper.UserAccountMapper;
import com.damochaohe.user.service.AdminUserWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 后台用户写服务实现。
 */
@Service
@RequiredArgsConstructor
public class AdminUserWriteServiceImpl implements AdminUserWriteService {

    private final UserAccountMapper userAccountMapper;

    @Override
    public void updateUserStatus(Long userId, Integer status) {
        UserAccountEntity user = userAccountMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setStatus(status);
        userAccountMapper.updateById(user);
    }
}
