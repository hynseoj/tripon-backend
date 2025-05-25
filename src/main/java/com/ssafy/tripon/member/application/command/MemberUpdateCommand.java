package com.ssafy.tripon.member.application.command;

import com.ssafy.tripon.member.domain.Member;

public record MemberUpdateCommand(
        String email,
        String name,
        String password
) {
    public Member toMember(String profileImageName, String profileImageUrl) {
        return new Member(email, name, password,profileImageName, profileImageUrl);
    }

	public Member toMember(String passwordEncode, String profileImageName, String profileImageUrl) {
        return new Member(email, name, passwordEncode,profileImageName, profileImageUrl);
	}

}
