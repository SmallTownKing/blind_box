package com.damochaohe.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.common.security.LoginUser;
import com.damochaohe.infra.security.JwtTokenService;
import com.damochaohe.user.entity.AdminPermissionEntity;
import com.damochaohe.user.entity.AdminRoleEntity;
import com.damochaohe.user.entity.AdminRolePermissionEntity;
import com.damochaohe.user.entity.AdminUserEntity;
import com.damochaohe.user.entity.AdminUserRoleEntity;
import com.damochaohe.user.mapper.AdminPermissionMapper;
import com.damochaohe.user.mapper.AdminRoleMapper;
import com.damochaohe.user.mapper.AdminRolePermissionMapper;
import com.damochaohe.user.mapper.AdminUserMapper;
import com.damochaohe.user.mapper.AdminUserRoleMapper;
import com.damochaohe.user.dto.AdminLoginRequest;
import com.damochaohe.user.dto.AdminLoginResponse;
import com.damochaohe.user.dto.AdminPermissionResponse;
import com.damochaohe.user.service.AdminAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final AdminUserRoleMapper adminUserRoleMapper;
    private final AdminRoleMapper adminRoleMapper;
    private final AdminRolePermissionMapper adminRolePermissionMapper;
    private final AdminPermissionMapper adminPermissionMapper;
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

        List<String> roles = findRoleCodes(adminUser.getId());
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
    public AdminPermissionResponse getPermissionInfo(Long adminUserId) {
        AdminUserEntity adminUser = adminUserMapper.selectById(adminUserId);
        if (adminUser == null) {
            throw new BusinessException("管理员不存在");
        }
        List<String> roles = findRoleCodes(adminUserId);
        return AdminPermissionResponse.builder()
                .roles(roles)
                .permissions(findPermissionCodes(roles))
                .build();
    }

    private List<String> findRoleCodes(Long adminUserId) {
        List<Long> roleIds = adminUserRoleMapper.selectList(new LambdaQueryWrapper<AdminUserRoleEntity>()
                        .eq(AdminUserRoleEntity::getAdminUserId, adminUserId))
                .stream()
                .map(AdminUserRoleEntity::getRoleId)
                .toList();
        if (roleIds.isEmpty()) {
            return List.of("SUPER_ADMIN");
        }
        List<String> roleCodes = adminRoleMapper.selectBatchIds(roleIds)
                .stream()
                .filter(item -> item.getStatus() != null && item.getStatus() == 1)
                .map(AdminRoleEntity::getRoleCode)
                .toList();
        return roleCodes.isEmpty() ? List.of("SUPER_ADMIN") : roleCodes;
    }

    private List<String> findPermissionCodes(List<String> roles) {
        if (roles.isEmpty()) {
            return List.of();
        }
        List<Long> roleIds = adminRoleMapper.selectList(new LambdaQueryWrapper<AdminRoleEntity>()
                        .in(AdminRoleEntity::getRoleCode, roles)
                        .eq(AdminRoleEntity::getStatus, 1))
                .stream()
                .map(AdminRoleEntity::getId)
                .toList();
        if (roleIds.isEmpty()) {
            return List.of();
        }
        Set<Long> permissionIds = adminRolePermissionMapper.selectList(new LambdaQueryWrapper<AdminRolePermissionEntity>()
                        .in(AdminRolePermissionEntity::getRoleId, roleIds))
                .stream()
                .map(AdminRolePermissionEntity::getPermissionId)
                .collect(Collectors.toSet());
        if (permissionIds.isEmpty()) {
            return List.of();
        }
        return adminPermissionMapper.selectBatchIds(permissionIds)
                .stream()
                .filter(item -> item.getStatus() != null && item.getStatus() == 1)
                .map(AdminPermissionEntity::getPermissionCode)
                .toList();
    }
}
