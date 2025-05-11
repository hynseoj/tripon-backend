package com.ssafy.tripon.review.presentation.request;

import com.ssafy.tripon.review.application.command.ReviewSaveCommand;

import jakarta.validation.constraints.NotBlank;

public record ReviewSaveRequest(
		@NotBlank(message = "제목을 입력해주세요.")
        String title
) {
    public ReviewSaveCommand toCommand(String email) {
        return new ReviewSaveCommand(email, title);
    }
}
