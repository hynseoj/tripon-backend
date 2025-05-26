package com.ssafy.tripon.review.presentation.request;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.tripon.review.application.command.ReviewSaveCommand;

import jakarta.validation.constraints.NotBlank;

public record ReviewSaveRequest(
		@NotBlank(message = "제목을 입력해주세요.")
        String title
) {
    public ReviewSaveCommand toCommand(String email, MultipartFile thumbnail) {
        return new ReviewSaveCommand(email, title, thumbnail);
    }
}
