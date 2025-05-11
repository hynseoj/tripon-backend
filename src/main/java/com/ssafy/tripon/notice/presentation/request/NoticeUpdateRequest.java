package com.ssafy.tripon.notice.presentation.request;

import com.ssafy.tripon.notice.application.command.NoticeUpdateCommand;

import jakarta.validation.constraints.NotBlank;

public record NoticeUpdateRequest(
		@NotBlank(message = "제목을 입력해주세요.")
        String title,
        @NotBlank(message = "내용을 입력해주세요.")
        String content
) {
    public NoticeUpdateCommand toCommand(Integer id, String email) {
        return new NoticeUpdateCommand(id, email, title, content);
    }
}
