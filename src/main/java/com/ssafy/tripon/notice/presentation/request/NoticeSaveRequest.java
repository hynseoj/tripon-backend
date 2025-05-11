package com.ssafy.tripon.notice.presentation.request;

import com.ssafy.tripon.notice.application.command.NoticeSaveCommand;

import jakarta.validation.constraints.NotBlank;

public record NoticeSaveRequest(
		@NotBlank(message = "제목을 입력해주세요.")
        String title,
        @NotBlank(message = "내용을 입력해주세요.")
        String content
) {
    public NoticeSaveCommand toCommand(String email) {
        return new NoticeSaveCommand(email, title, content);
    }
}
