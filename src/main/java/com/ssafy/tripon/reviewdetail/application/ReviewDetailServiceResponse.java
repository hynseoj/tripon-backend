package com.ssafy.tripon.reviewdetail.application;

import com.ssafy.tripon.reviewdetail.domain.ReviewDetail;
import com.ssafy.tripon.reviewdetail.presentation.response.ReviewDetailSaveResponse;
import java.time.LocalDateTime;

public record ReviewDetailServiceResponse(
        Integer id,
        Integer reviewId,
        Integer day,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewDetailSaveResponse from(ReviewDetail reviewDetail) {
        return new ReviewDetailSaveResponse(
                reviewDetail.getId(),
                reviewDetail.getReviewId(),
                reviewDetail.getDay(),
                reviewDetail.getContent(),
                reviewDetail.getCreatedAt(),
                reviewDetail.getUpdatedAt()
        );
    }
}
