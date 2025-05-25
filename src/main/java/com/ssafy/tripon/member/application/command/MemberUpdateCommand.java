package com.ssafy.tripon.member.application.command;

import com.ssafy.tripon.member.domain.Member;

public record MemberUpdateCommand(
        String email,
        String name,
        String password,
        String profileImageName, 
        String profileImageUrl
) {
    public Member toMember() {
        return new Member(email, name, password,profileImageName, profileImageUrl);
    }

	public Member toMember(String passwordEncode) {
        return new Member(email, name, passwordEncode,profileImageName, profileImageUrl);
	}

}
