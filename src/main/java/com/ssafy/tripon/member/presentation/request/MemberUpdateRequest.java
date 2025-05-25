package com.ssafy.tripon.member.presentation.request;

import com.ssafy.tripon.member.application.command.MemberUpdateCommand;
import jakarta.validation.constraints.NotBlank;

public record MemberUpdateRequest(
        @NotBlank
        String name,
        String password,
        String profileImageName, 
        String profileImageUrl
) {
    public MemberUpdateCommand toCommand(String email) {
        return new MemberUpdateCommand(email, name, password, profileImageName, 
                profileImageUrl);
    }
}
