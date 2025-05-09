package com.ssafy.tripon.review.presentation.response;

import com.ssafy.tripon.review.application.ReviewServiceResponse;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewFindResponse(
        Integer id,
        String memberEmail,
        String title,
        List<Integer> details,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewFindResponse from(ReviewServiceResponse response) {
        return new ReviewFindResponse(
                response.id(),
                response.memberEmail(),
                response.title(),
                response.details(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
