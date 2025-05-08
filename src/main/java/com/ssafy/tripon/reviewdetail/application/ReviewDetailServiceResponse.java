package com.ssafy.tripon.reviewdetail.application;

import com.ssafy.tripon.reviewdetail.domain.ReviewDetail;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewDetailServiceResponse(
        Integer id,
        Integer reviewId,
        Integer day,
        String content,
        List<Integer> attractions, // reviewdetailId로 조회한 관광지 목록
        List<String> pictures, // reviewdetailId로 조회한 후기 사진 목록
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewDetailServiceResponse from(ReviewDetail reviewDetail, List<Integer> attractions, List<String> pictures) {
        return new ReviewDetailServiceResponse(
                reviewDetail.getId(),
                reviewDetail.getReviewId(),
                reviewDetail.getDay(),
                reviewDetail.getContent(),
                attractions,
                pictures,
                reviewDetail.getCreatedAt(),
                reviewDetail.getUpdatedAt()
        );
    }
}
