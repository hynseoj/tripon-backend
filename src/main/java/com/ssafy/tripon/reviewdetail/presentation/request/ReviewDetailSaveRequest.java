package com.ssafy.tripon.reviewdetail.presentation.request;

import com.ssafy.tripon.reviewdetail.application.command.ReviewDetailSaveCommand;
import java.util.List;

public record ReviewDetailSaveRequest(
        Integer day,
        String content,
        List<Integer> attractions
) {
    public ReviewDetailSaveCommand toCommand(Integer reviewId) {
        return new ReviewDetailSaveCommand(reviewId, day, content, attractions);
    }
}
