package com.ssafy.tripon.member.presentation.request;

import com.ssafy.tripon.member.application.command.MemberLoginCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {
    public MemberLoginCommand toCommand() {
        return new MemberLoginCommand(email, password);
    }
}
