package com.ssafy.tripon.member.application.command;

import com.ssafy.tripon.member.domain.Member;

public record MemberRegisterCommand(
        String email,
        String name,
        String password
) {
    public Member toMember(String password) {
        return new Member(email, name, password);
    }

    public Member toMember(String password, String profileImageName, String profileImageUrl) {
        return new Member(email, name, password, profileImageName, profileImageUrl);
    }
}
