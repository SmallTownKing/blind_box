package com.damochaohe.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.common.security.LoginUser;
import com.damochaohe.infra.security.JwtTokenService;
import com.damochaohe.user.entity.AdminUserEntity;
import com.damochaohe.user.mapper.AdminUserMapper;
import com.damochaohe.user.dto.AdminLoginRequest;
import com.damochaohe.user.dto.AdminLoginResponse;
import com.damochaohe.user.dto.AdminPermissionResponse;
import com.damochaohe.user.service.AdminAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * 后台管理员认证实现。
 *
 * <p>当前为演示实现，后续接入管理员表、密码加密校验与权限表。</p>
 */
@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private final JwtTokenService jwtTokenService;
    private final AdminUserMapper adminUserMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public AdminLoginResponse login(AdminLoginRequest request) {
        AdminUserEntity adminUser = adminUserMapper.selectOne(new LambdaQueryWrapper<AdminUserEntity>()
                .eq(AdminUserEntity::getUsername, request.getUsername())
                .last("limit 1"));

        if (adminUser == null) {
            throw new BusinessException("管理员账号不存在");
        }
        if (!Integer.valueOf(1).equals(adminUser.getStatus())) {
            throw new BusinessException("管理员账号已被禁用");
        }
        boolean passwordMatched = adminUser.getPassword().equals(request.getPassword())
                || bCryptPasswordEncoder.matches(request.getPassword(), adminUser.getPassword());
        if (!passwordMatched) {
            throw new BusinessException("账号或密码错误");
        }

        List<String> roles = List.of("SUPER_ADMIN");
        LoginUser loginUser = LoginUser.builder()
                .userId(adminUser.getId())
                .username(adminUser.getUsername())
                .nickname(adminUser.getRealName())
                .tokenType("admin")
                .roles(roles)
                .build();

        return AdminLoginResponse.builder()
                .accessToken(jwtTokenService.generateToken(loginUser))
                .tokenType("Bearer")
                .name(adminUser.getRealName())
                .roles(roles)
                .build();
    }

    @Override
    public AdminPermissionResponse getPermissionInfo() {
        return AdminPermissionResponse.builder()
                .roles(List.of("SUPER_ADMIN"))
                .permissions(List.of(
                        "system:user:view",
                        "system:user:edit",
                        "system:content:view",
                        "system:content:edit",
                        "system:dict:view",
                        "system:dict:edit"))
                .build();
    }
}
