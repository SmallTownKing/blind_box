package com.damochaohe.user.service.impl;

import com.damochaohe.common.security.LoginUser;
import com.damochaohe.infra.security.JwtTokenService;
import com.damochaohe.user.dto.LoginRequest;
import com.damochaohe.user.dto.LoginResponse;
import com.damochaohe.user.dto.UserProfileResponse;
import com.damochaohe.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public LoginResponse mobileCodeLogin(LoginRequest request) {
        UserProfileResponse profile = mockProfile(request.getMobile());
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
    public UserProfileResponse getUserProfile(Long userId) {
        return UserProfileResponse.builder()
                .userId(userId)
                .nickname("潮盒用户" + userId)
                .avatar("https://static.example.com/avatar/default.png")
                .mobile("13800138000")
                .currentMemberLevel(1)
                .build();
    }

    private UserProfileResponse mockProfile(String mobile) {
        return UserProfileResponse.builder()
                .userId(10001L)
                .nickname("新注册用户")
                .avatar("https://static.example.com/avatar/default.png")
                .mobile(mobile)
                .currentMemberLevel(1)
                .build();
    }
}
