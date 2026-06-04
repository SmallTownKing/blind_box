package com.damochaohe.app.support;

import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.common.security.LoginUser;
import com.damochaohe.common.security.SecurityConstants;
import com.damochaohe.infra.security.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * APP 端当前登录用户解析器。
 */
@Component
@RequiredArgsConstructor
public class AppLoginUserResolver {

    private final JwtTokenService jwtTokenService;

    public Long currentUserId(HttpServletRequest request) {
        LoginUser loginUser = currentLoginUser(request);
        if (loginUser.getUserId() == null) {
            throw new BusinessException("登录态无效");
        }
        return loginUser.getUserId();
    }

    public LoginUser currentLoginUser(HttpServletRequest request) {
        String authorization = request.getHeader(SecurityConstants.AUTHORIZATION);
        if (authorization == null || authorization.isBlank()) {
            throw new BusinessException("未登录或登录已失效");
        }
        String token = authorization;
        if (authorization.startsWith(SecurityConstants.BEARER_PREFIX)) {
            token = authorization.substring(SecurityConstants.BEARER_PREFIX.length()).trim();
        }
        LoginUser loginUser = jwtTokenService.parseToken(token);
        if (loginUser == null) {
            throw new BusinessException("未登录或登录已失效");
        }
        return loginUser;
    }
}
