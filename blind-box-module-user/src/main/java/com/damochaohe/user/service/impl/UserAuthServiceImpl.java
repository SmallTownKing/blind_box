package com.damochaohe.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.common.security.LoginUser;
import com.damochaohe.infra.security.JwtTokenService;
import com.damochaohe.user.dto.GuestLoginRequest;
import com.damochaohe.user.dto.LoginRequest;
import com.damochaohe.user.dto.LoginResponse;
import com.damochaohe.user.dto.MobilePasswordLoginRequest;
import com.damochaohe.user.dto.UserBindMobileRequest;
import com.damochaohe.user.dto.UserPasswordUpdateRequest;
import com.damochaohe.user.dto.UserProfileUpdateRequest;
import com.damochaohe.user.dto.UserProfileResponse;
import com.damochaohe.user.entity.UserAccountEntity;
import com.damochaohe.user.mapper.UserAccountMapper;
import com.damochaohe.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

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
    public LoginResponse mobilePasswordLogin(MobilePasswordLoginRequest request) {
        UserAccountEntity account = userAccountMapper.selectOne(new LambdaQueryWrapper<UserAccountEntity>()
                .eq(UserAccountEntity::getMobile, request.getMobile())
                .last("limit 1"));
        if (account == null) {
            throw new BusinessException("手机号未注册");
        }
        if (account.getPasswordHash() == null || account.getPasswordHash().isBlank()) {
            throw new BusinessException("该账号暂未设置登录密码");
        }
        boolean passwordMatched = account.getPasswordHash().equals(request.getPassword())
                || bCryptPasswordEncoder.matches(request.getPassword(), account.getPasswordHash());
        if (!passwordMatched) {
            throw new BusinessException("手机号或密码错误");
        }
        return buildLoginResponse(account, request.getMobile(), List.of("USER"));
    }

    @Override
    public LoginResponse guestLogin(GuestLoginRequest request) {
        UserAccountEntity account = createGuestUser(request.getDeviceId());
        return buildLoginResponse(account, account.getUserNo(), List.of("USER", "GUEST"));
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
    public UserProfileResponse bindMobile(Long userId, UserBindMobileRequest request) {
        UserAccountEntity entity = getRequiredUser(userId);
        UserAccountEntity existed = userAccountMapper.selectOne(new LambdaQueryWrapper<UserAccountEntity>()
                .eq(UserAccountEntity::getMobile, request.getMobile())
                .last("limit 1"));
        if (existed != null && !existed.getId().equals(entity.getId())) {
            throw new BusinessException("该手机号已绑定其他账号");
        }
        entity.setMobile(request.getMobile().trim());
        entity.setUpdatedAt(LocalDateTime.now());
        userAccountMapper.updateById(entity);
        return toProfile(entity);
    }

    @Override
    public UserProfileResponse updateUserProfile(Long userId, UserProfileUpdateRequest request) {
        UserAccountEntity entity = getRequiredUser(userId);
        if (request.getNickname() != null && !request.getNickname().isBlank()) {
            entity.setNickname(request.getNickname().trim());
        }
        if (request.getAvatar() != null && !request.getAvatar().isBlank()) {
            entity.setAvatar(request.getAvatar().trim());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            entity.setEmail(request.getEmail().trim());
        }
        if (request.getBirthday() != null) {
            validateBirthdayUpdatable(entity, request.getBirthday());
            entity.setBirthday(request.getBirthday());
        }
        entity.setUpdatedAt(LocalDateTime.now());
        userAccountMapper.updateById(entity);
        return toProfile(entity);
    }

    @Override
    public void updatePassword(Long userId, UserPasswordUpdateRequest request) {
        UserAccountEntity entity = getRequiredUser(userId);
        if (entity.getPasswordHash() != null && !entity.getPasswordHash().isBlank()) {
            if (request.getOldPassword() == null || request.getOldPassword().isBlank()) {
                throw new BusinessException("请输入旧密码");
            }
            boolean matched = entity.getPasswordHash().equals(request.getOldPassword())
                    || bCryptPasswordEncoder.matches(request.getOldPassword(), entity.getPasswordHash());
            if (!matched) {
                throw new BusinessException("旧密码不正确");
            }
        }
        entity.setPasswordHash(bCryptPasswordEncoder.encode(request.getNewPassword()));
        entity.setUpdatedAt(LocalDateTime.now());
        userAccountMapper.updateById(entity);
    }

    private UserAccountEntity createMobileUser(String mobile) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUserNo("U" + System.currentTimeMillis());
        entity.setNickname("新注册用户");
        entity.setAvatar("https://static.example.com/avatar/default.png");
        entity.setMobile(mobile);
        entity.setEmail(null);
        entity.setBirthday(null);
        entity.setPasswordHash(null);
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
        entity.setEmail(null);
        entity.setBirthday(null);
        entity.setPasswordHash(null);
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
                .userNo(entity.getUserNo())
                .nickname(entity.getNickname())
                .avatar(entity.getAvatar())
                .mobile(entity.getMobile())
                .mobileBound(entity.getMobile() != null && !entity.getMobile().isBlank())
                .email(entity.getEmail())
                .birthday(entity.getBirthday())
                .birthdayEditable(entity.getBirthday() == null)
                .passwordSet(entity.getPasswordHash() != null && !entity.getPasswordHash().isBlank())
                .currentMemberLevel(entity.getCurrentMemberLevel())
                .registerSource(entity.getRegisterSource())
                .guestUser(entity.getMobile() == null || entity.getMobile().isBlank())
                .build();
    }

    private LoginResponse buildLoginResponse(UserAccountEntity account, String username, List<String> roles) {
        UserProfileResponse profile = toProfile(account);
        LoginUser loginUser = LoginUser.builder()
                .userId(profile.getUserId())
                .username(username)
                .nickname(profile.getNickname())
                .tokenType("app")
                .roles(roles)
                .build();
        return LoginResponse.builder()
                .accessToken(jwtTokenService.generateToken(loginUser))
                .tokenType("Bearer")
                .profile(profile)
                .build();
    }

    private UserAccountEntity getRequiredUser(Long userId) {
        UserAccountEntity entity = userAccountMapper.selectById(userId);
        if (entity == null) {
            return ensureDefaultUser(userId);
        }
        return entity;
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
        entity.setEmail(null);
        entity.setBirthday(null);
        entity.setPasswordHash(null);
        entity.setStatus(1);
        entity.setRegisterSource("MOBILE_CODE");
        entity.setCurrentMemberLevel(1);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        userAccountMapper.insert(entity);
        return entity;
    }

    private void validateBirthdayUpdatable(UserAccountEntity entity, LocalDate birthday) {
        if (entity.getBirthday() != null && !entity.getBirthday().equals(birthday)) {
            throw new BusinessException("生日仅允许设置一次");
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new BusinessException("生日不能晚于当前日期");
        }
    }
}
