package com.ssafy.tripon.review.application;

import com.ssafy.tripon.review.domain.Review;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record ReviewServiceResponse(
        Integer id,
        String memberEmail,
        String title,
        List<Integer> details,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewServiceResponse from(Review review) {
        return new ReviewServiceResponse(
                review.getId(),
                review.getMemberEmail(),
                review.getTitle(),
                Collections.emptyList(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }

    public static ReviewServiceResponse from(Review review, List<Integer> details) {
        return new ReviewServiceResponse(
                review.getId(),
                review.getMemberEmail(),
                review.getTitle(),
                details,
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
