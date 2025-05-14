package com.ssafy.tripon.member.presentation.response;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
