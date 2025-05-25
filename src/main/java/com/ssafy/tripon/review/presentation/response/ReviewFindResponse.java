package com.ssafy.tripon.review.presentation.response;

import com.ssafy.tripon.review.application.ReviewServiceResponse;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewFindResponse(
        Integer id,
        String memberEmail,
        String memberName,
        String title,
        Integer likes,
        Boolean isLiked,
        List<Integer> details,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewFindResponse from(ReviewServiceResponse response) {
        return new ReviewFindResponse(
                response.id(),
                response.memberEmail(),
                response.memberName(),
                response.title(),
                response.likes(),
                response.isLiked(),
                response.details(),
                response.createdAt(),
                response.updatedAt()
                
        );
    }
}
