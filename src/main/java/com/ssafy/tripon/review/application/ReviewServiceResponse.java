package com.ssafy.tripon.review.application;

import com.ssafy.tripon.review.domain.Review;
import java.time.LocalDateTime;

public record ReviewServiceResponse(
        Integer id,
        String memberEmail,
        String title,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewServiceResponse from(Review review) {
        return new ReviewServiceResponse(
                review.getId(),
                review.getMemberEmail(),
                review.getTitle(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
