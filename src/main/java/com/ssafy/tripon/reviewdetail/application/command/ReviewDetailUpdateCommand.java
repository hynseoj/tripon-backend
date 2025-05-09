package com.ssafy.tripon.reviewdetail.application.command;

import com.ssafy.tripon.reviewdetail.domain.ReviewDetail;
import java.util.List;

public record ReviewDetailUpdateCommand(
        Integer id,
        Integer reviewId,
        Integer day,
        String content,
        List<Integer> attractions
) {
    public ReviewDetail toReviewDetail() {
        return new ReviewDetail(id, reviewId, day, content);
    }
}
