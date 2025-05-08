package com.ssafy.tripon.review.presentation.response;

import com.ssafy.tripon.review.application.ReviewServiceResponse;
import java.time.LocalDateTime;

public record ReviewFindResponse(
        Integer id,
        String memberEmail,
        String title,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewFindResponse from(ReviewServiceResponse response) {
        return new ReviewFindResponse(
                response.id(),
                response.memberEmail(),
                response.title(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
