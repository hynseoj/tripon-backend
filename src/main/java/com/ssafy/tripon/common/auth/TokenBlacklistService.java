package com.ssafy.tripon.common.auth;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String PREFIX = "blacklist:";

    public void blacklistToken(String jti, Duration ttl) {
        redisTemplate.opsForValue().set(PREFIX + jti, "true", ttl);
    }

    public boolean isBlacklisted(String jti) {
        return "true".equals(redisTemplate.opsForValue().get(PREFIX + jti));
    }
}
