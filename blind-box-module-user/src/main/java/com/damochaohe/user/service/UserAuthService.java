package com.damochaohe.user.service;

import com.damochaohe.user.dto.LoginRequest;
import com.damochaohe.user.dto.LoginResponse;
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
     * 查询当前用户资料。
     *
     * @param userId 用户 ID
     * @return 用户资料
     */
    UserProfileResponse getUserProfile(Long userId);
}
