package com.ssafy.tripon.common.auth;

public record TokenPair(
        Token accessToken,
        Token refreshToken
) {
}
