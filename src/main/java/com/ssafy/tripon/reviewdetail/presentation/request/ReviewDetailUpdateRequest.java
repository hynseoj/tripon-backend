package com.ssafy.tripon.reviewdetail.presentation.request;

import com.ssafy.tripon.reviewdetail.application.command.ReviewDetailUpdateCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ReviewDetailUpdateRequest(
		@NotBlank
		@Positive
        Integer day,
        @NotBlank(message = "내용을 입력해주세요.")
        String content,
        List<Integer> attractions
) {
    public ReviewDetailUpdateCommand toCommand(Integer reviewDetailId, Integer reviewId) {
        return new ReviewDetailUpdateCommand(reviewDetailId, reviewId, day, content, attractions);
    }
}
