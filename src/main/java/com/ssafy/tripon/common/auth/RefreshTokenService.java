package com.ssafy.tripon.common.auth;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String PREFIX = "refresh:";

    public void saveRefreshToken(String memberEmail, JwtToken refreshToken, Duration ttl) {
        String key = PREFIX + memberEmail;
        redisTemplate.opsForValue().set(key, refreshToken.token(), ttl);
    }

    public JwtToken getRefreshToken(String memberEmail) {
        return new JwtToken((String) redisTemplate.opsForValue().get(PREFIX + memberEmail));
    }

    public void deleteRefreshToken(String memberEmail) {
        redisTemplate.delete(PREFIX + memberEmail);
    }
}
