package com.damochaohe.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.common.security.LoginUser;
import com.damochaohe.infra.security.JwtTokenService;
import com.damochaohe.user.dto.GuestLoginRequest;
import com.damochaohe.user.dto.LoginRequest;
import com.damochaohe.user.dto.LoginResponse;
import com.damochaohe.user.dto.UserProfileUpdateRequest;
import com.damochaohe.user.dto.UserProfileResponse;
import com.damochaohe.user.entity.UserAccountEntity;
import com.damochaohe.user.mapper.UserAccountMapper;
import com.damochaohe.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户认证服务实现。
 *
 * <p>当前使用演示数据完成工程打通，后续替换为数据库持久化实现。</p>
 */
@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final JwtTokenService jwtTokenService;
    private final UserAccountMapper userAccountMapper;

    @Override
    public LoginResponse mobileCodeLogin(LoginRequest request) {
        UserAccountEntity account = userAccountMapper.selectOne(new LambdaQueryWrapper<UserAccountEntity>()
                .eq(UserAccountEntity::getMobile, request.getMobile())
                .last("limit 1"));
        if (account == null) {
            account = createMobileUser(request.getMobile());
        }
        UserProfileResponse profile = toProfile(account);
        LoginUser loginUser = LoginUser.builder()
                .userId(profile.getUserId())
                .username(request.getMobile())
                .nickname(profile.getNickname())
                .tokenType("app")
                .roles(List.of("USER"))
                .build();

        return LoginResponse.builder()
                .accessToken(jwtTokenService.generateToken(loginUser))
                .tokenType("Bearer")
                .profile(profile)
                .build();
    }

    @Override
    public LoginResponse guestLogin(GuestLoginRequest request) {
        UserAccountEntity account = createGuestUser(request.getDeviceId());
        UserProfileResponse profile = toProfile(account);
        LoginUser loginUser = LoginUser.builder()
                .userId(profile.getUserId())
                .username(account.getUserNo())
                .nickname(profile.getNickname())
                .tokenType("app")
                .roles(List.of("USER", "GUEST"))
                .build();

        return LoginResponse.builder()
                .accessToken(jwtTokenService.generateToken(loginUser))
                .tokenType("Bearer")
                .profile(profile)
                .build();
    }

    @Override
    public UserProfileResponse getUserProfile(Long userId) {
        UserAccountEntity entity = userAccountMapper.selectById(userId);
        if (entity == null) {
            entity = ensureDefaultUser(userId);
        }
        return toProfile(entity);
    }

    @Override
    public UserProfileResponse updateUserProfile(Long userId, UserProfileUpdateRequest request) {
        UserAccountEntity entity = userAccountMapper.selectById(userId);
        if (entity == null) {
            entity = ensureDefaultUser(userId);
        }
        entity.setNickname(request.getNickname());
        if (request.getAvatar() != null && !request.getAvatar().isBlank()) {
            entity.setAvatar(request.getAvatar());
        }
        entity.setUpdatedAt(LocalDateTime.now());
        userAccountMapper.updateById(entity);
        return toProfile(entity);
    }

    private UserAccountEntity createMobileUser(String mobile) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUserNo("U" + System.currentTimeMillis());
        entity.setNickname("新注册用户");
        entity.setAvatar("https://static.example.com/avatar/default.png");
        entity.setMobile(mobile);
        entity.setStatus(1);
        entity.setRegisterSource("MOBILE_CODE");
        entity.setCurrentMemberLevel(1);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        userAccountMapper.insert(entity);
        return entity;
    }

    private UserAccountEntity createGuestUser(String deviceId) {
        UserAccountEntity entity = new UserAccountEntity();
        String suffix = String.valueOf(System.currentTimeMillis());
        entity.setUserNo("G" + suffix);
        entity.setNickname("游客" + suffix.substring(Math.max(0, suffix.length() - 4)));
        entity.setAvatar("https://static.example.com/avatar/default.png");
        entity.setMobile(null);
        entity.setStatus(1);
        entity.setRegisterSource(deviceId == null || deviceId.isBlank() ? "GUEST" : "GUEST:" + deviceId);
        entity.setCurrentMemberLevel(1);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        userAccountMapper.insert(entity);
        return entity;
    }

    private UserProfileResponse toProfile(UserAccountEntity entity) {
        return UserProfileResponse.builder()
                .userId(entity.getId())
                .nickname(entity.getNickname())
                .avatar(entity.getAvatar())
                .mobile(entity.getMobile())
                .currentMemberLevel(entity.getCurrentMemberLevel())
                .registerSource(entity.getRegisterSource())
                .guestUser(entity.getMobile() == null || entity.getMobile().isBlank())
                .build();
    }

    private UserAccountEntity ensureDefaultUser(Long userId) {
        if (userId == null) {
            throw new BusinessException("用户不存在");
        }
        UserAccountEntity entity = new UserAccountEntity();
        entity.setId(userId);
        entity.setUserNo("U" + userId);
        entity.setNickname("潮盒用户" + userId);
        entity.setAvatar("https://static.example.com/avatar/default.png");
        entity.setMobile("13800138000");
        entity.setStatus(1);
        entity.setRegisterSource("MOBILE_CODE");
        entity.setCurrentMemberLevel(1);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        userAccountMapper.insert(entity);
        return entity;
    }
}
