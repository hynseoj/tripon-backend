package com.ssafy.tripon.reviewdetail.presentation.response;

import java.time.LocalDateTime;

public record ReviewDetailSaveResponse(
        Integer id,
        Integer reviewId,
        Integer day,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewDetailSaveResponse from(ReviewDetailSaveResponse response) {
        return new ReviewDetailSaveResponse(
                response.id(),
                response.reviewId(),
                response.day(),
                response.content(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
