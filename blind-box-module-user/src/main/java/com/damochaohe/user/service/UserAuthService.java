package com.damochaohe.user.service;

import com.damochaohe.user.dto.LoginRequest;
import com.damochaohe.user.dto.LoginResponse;
import com.damochaohe.user.dto.MobilePasswordLoginRequest;
import com.damochaohe.user.dto.GuestLoginRequest;
import com.damochaohe.user.dto.UserBindMobileRequest;
import com.damochaohe.user.dto.UserPasswordUpdateRequest;
import com.damochaohe.user.dto.UserProfileUpdateRequest;
import com.damochaohe.user.dto.UserProfileResponse;

/**
 * 用户认证服务。
 */
public interface UserAuthService {

    /**
     * 手机号验证码登录。
     *
     * <p>后续可接入真实短信校验、自动注册、邀请码绑定等逻辑。</p>
     *
     * @param request 登录参数
     * @return 登录结果
     */
    LoginResponse mobileCodeLogin(LoginRequest request);

    /**
     * 手机号密码登录。
     *
     * @param request 登录参数
     * @return 登录结果
     */
    LoginResponse mobilePasswordLogin(MobilePasswordLoginRequest request);

    /**
     * 游客登录。
     *
     * @param request 游客登录参数
     * @return 登录结果
     */
    LoginResponse guestLogin(GuestLoginRequest request);

    /**
     * 查询当前用户资料。
     *
     * @param userId 用户 ID
     * @return 用户资料
     */
    UserProfileResponse getUserProfile(Long userId);

    /**
     * 绑定手机号。
     *
     * @param userId 用户 ID
     * @param request 绑定参数
     * @return 更新后的资料
     */
    UserProfileResponse bindMobile(Long userId, UserBindMobileRequest request);

    /**
     * 更新用户资料。
     *
     * @param userId 用户 ID
     * @param request 更新参数
     * @return 更新后的资料
     */
    UserProfileResponse updateUserProfile(Long userId, UserProfileUpdateRequest request);

    /**
     * 设置或修改登录密码。
     *
     * @param userId 用户 ID
     * @param request 密码参数
     */
    void updatePassword(Long userId, UserPasswordUpdateRequest request);
}
