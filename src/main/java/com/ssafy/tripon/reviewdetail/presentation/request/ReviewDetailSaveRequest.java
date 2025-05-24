package com.ssafy.tripon.reviewdetail.presentation.request;

import com.ssafy.tripon.reviewdetail.application.command.ReviewDetailSaveCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ReviewDetailSaveRequest(
		@NotNull
		@Positive
        Integer day,
        @NotBlank(message = "내용을 입력해주세요.")
        String content,
        List<Integer> attractions
) {
    public ReviewDetailSaveCommand toCommand(Integer reviewId) {
        return new ReviewDetailSaveCommand(reviewId, day, content, attractions);
    }
}
