package com.ssafy.tripon.review.application;

import com.ssafy.tripon.review.domain.Review;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record ReviewServiceResponse(
        Integer id,
        String memberEmail,
        String memberName,
        String profileImageUrl,
        String title,
        Integer likes,
        Boolean isLiked,
        List<Integer> details,
        String thumbnailUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewServiceResponse from(Review review, Integer likes, boolean isLiked) {
        return new ReviewServiceResponse(
                review.getId(),
                review.getMemberEmail(),
                "",
                "",
                review.getTitle(),
                likes,
                isLiked,
                Collections.emptyList(),
                review.getThumbnailUrl(),
                review.getCreatedAt(),
                review.getUpdatedAt()
               
        );
    }

    public static ReviewServiceResponse from(Review review, List<Integer> details, Integer likes, boolean isLiked, String memberName, String profileImageUrl) {
        return new ReviewServiceResponse(
                review.getId(),
                review.getMemberEmail(),
                memberName,
                profileImageUrl,
                review.getTitle(),
                likes,
                isLiked,
                details,
                review.getThumbnailUrl(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
    
    public static ReviewServiceResponse from(Review review) {
        return new ReviewServiceResponse(
                review.getId(),
                review.getMemberEmail(),
                "",
                "",
                review.getTitle(),
                null,
                null,
                Collections.emptyList(),
                review.getThumbnailUrl(),
                review.getCreatedAt(),
                review.getUpdatedAt()
               
        );
    }

    public static ReviewServiceResponse from(Review review, List<Integer> details) {
        return new ReviewServiceResponse(
                review.getId(),
                review.getMemberEmail(),
                "",
                "",
                review.getTitle(),
                null,
                null,
                details,
                review.getThumbnailUrl(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
