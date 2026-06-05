package com.damochaohe.admin.support;

import com.damochaohe.common.exception.BusinessException;
import com.damochaohe.common.security.LoginUser;
import com.damochaohe.common.security.SecurityConstants;
import com.damochaohe.infra.security.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminLoginUserResolver {

    private final JwtTokenService jwtTokenService;

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
        if (loginUser == null || loginUser.getUserId() == null) {
            throw new BusinessException("未登录或登录已失效");
        }
        return loginUser;
    }
}
