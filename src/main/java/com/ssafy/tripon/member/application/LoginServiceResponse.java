package com.ssafy.tripon.member.application;

import com.ssafy.tripon.common.auth.TokenPair;
import com.ssafy.tripon.member.domain.Member;

public record LoginServiceResponse(
        String name,
        TokenPair tokenPair
) {
    public static LoginServiceResponse of(Member member, TokenPair tokenPair) {
        return new LoginServiceResponse(member.getName(), tokenPair);
    }
}
