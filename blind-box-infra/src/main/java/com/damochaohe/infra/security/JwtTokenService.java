package com.damochaohe.infra.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.damochaohe.common.security.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

/**
 * JWT 令牌服务。
 *
 * <p>当前实现先满足基础登录态签发与解析，后续可扩展刷新令牌、黑名单与多端互斥策略。</p>
 */
@Component
public class JwtTokenService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expire-seconds}")
    private Long expireSeconds;

    public String generateToken(LoginUser loginUser) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(loginUser.getUserId()))
                .claim("loginUser", JSONUtil.toJsonStr(loginUser))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expireSeconds)))
                .signWith(getSecretKey())
                .compact();
    }

    public LoginUser parseToken(String token) {
        if (StrUtil.isBlank(token)) {
            return null;
        }
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String loginUserJson = claims.get("loginUser", String.class);
        return JSONUtil.toBean(loginUserJson, LoginUser.class);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
