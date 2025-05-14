package com.ssafy.tripon.member.application.command;

public record MemberLoginCommand(
        String email,
        String password
) {
}
