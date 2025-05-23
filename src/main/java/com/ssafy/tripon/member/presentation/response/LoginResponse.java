package com.ssafy.tripon.member.presentation.response;

import com.ssafy.tripon.member.application.LoginServiceResponse;

public record LoginResponse(
        String name,
        String accessToken
) {
    public static LoginResponse from(LoginServiceResponse response) {
        return new LoginResponse(response.name(), response.tokenPair().accessToken().token());
    }
}
