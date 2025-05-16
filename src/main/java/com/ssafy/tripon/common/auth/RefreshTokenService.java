package com.ssafy.tripon.common.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.refreshToken.expirationMillis}")
    private long refreshTokenExpirationMillis;

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String PREFIX = "refresh:";

    public void saveRefreshToken(String memberEmail, Token refreshToken) {
        String key = PREFIX + memberEmail;
        redisTemplate.opsForValue().set(key, refreshToken.token(), refreshTokenExpirationMillis);
    }

    public Token getRefreshToken(String memberEmail) {
        return new Token((String) redisTemplate.opsForValue().get(PREFIX + memberEmail));
    }

    public void deleteRefreshToken(String memberEmail) {
        redisTemplate.delete(PREFIX + memberEmail);
    }
}
