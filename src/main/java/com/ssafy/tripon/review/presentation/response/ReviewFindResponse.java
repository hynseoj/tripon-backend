package com.ssafy.tripon.review.presentation.response;

import com.ssafy.tripon.review.application.ReviewServiceResponse;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewFindResponse(
        Integer id,
        String memberEmail,
        String memberName,
        String ProfileImageUrl,
        String title,
        Integer likes,
        Boolean isLiked,
        List<Integer> details,
        String thumbnailUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewFindResponse from(ReviewServiceResponse response) {
        return new ReviewFindResponse(
                response.id(),
                response.memberEmail(),
                response.memberName(),
                response.profileImageUrl(),
                response.title(),
                response.likes(),
                response.isLiked(),
                response.details(),
                response.thumbnailUrl(),
                response.createdAt(),
                response.updatedAt()
                
        );
    }
}
