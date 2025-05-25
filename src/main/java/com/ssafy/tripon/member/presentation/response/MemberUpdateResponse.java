package com.ssafy.tripon.member.presentation.response;

import com.ssafy.tripon.member.application.LoginServiceResponse;

public record MemberUpdateResponse(
		String name,
		String accessToken,
		String profileImageUrl
		) {
	public static MemberUpdateResponse from(LoginServiceResponse response) {
		return new MemberUpdateResponse(response.name(), response.tokenPair().accessToken().token(), response.profileImageUrl());
	}
}
