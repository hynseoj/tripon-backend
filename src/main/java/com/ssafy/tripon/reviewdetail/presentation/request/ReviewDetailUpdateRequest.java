package com.ssafy.tripon.reviewdetail.presentation.request;

import com.ssafy.tripon.reviewdetail.application.command.ReviewDetailUpdateCommand;
import java.util.List;

public record ReviewDetailUpdateRequest(
        Integer day,
        String content,
        List<Integer> attractions
) {
    public ReviewDetailUpdateCommand toCommand(Integer reviewDetailId, Integer reviewId) {
        return new ReviewDetailUpdateCommand(reviewDetailId, reviewId, day, content, attractions);
    }
}
