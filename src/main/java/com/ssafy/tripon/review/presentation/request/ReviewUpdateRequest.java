package com.ssafy.tripon.review.presentation.request;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.tripon.review.application.command.ReviewUpdateCommand;

import jakarta.validation.constraints.NotBlank;

public record ReviewUpdateRequest(
		@NotBlank(message = "제목을 입력해주세요.")
        String title
) {
    public ReviewUpdateCommand toCommand(Integer id, String email, MultipartFile thumbnail, String thumbnailUrl) {
        return new ReviewUpdateCommand(id, email, title, thumbnail, thumbnailUrl);
    }
}