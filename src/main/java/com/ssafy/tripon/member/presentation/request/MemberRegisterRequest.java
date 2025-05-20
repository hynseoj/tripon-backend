package com.ssafy.tripon.member.presentation.request;

import com.ssafy.tripon.member.application.command.MemberRegisterCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberRegisterRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String name,

        @NotBlank
        String password
) {
    public MemberRegisterCommand toCommand() {
        return new MemberRegisterCommand(email, name, password);
    }
}
