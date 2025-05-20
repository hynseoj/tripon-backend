package com.ssafy.tripon.member.presentation.response;

import com.ssafy.tripon.common.auth.TokenPair;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
    public static LoginResponse from(TokenPair tokenPair) {
        return new LoginResponse(tokenPair.accessToken().token(), tokenPair.refreshToken().token());
    }
}
