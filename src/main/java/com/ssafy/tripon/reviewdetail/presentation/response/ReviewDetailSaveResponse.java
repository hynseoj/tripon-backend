package com.ssafy.tripon.reviewdetail.presentation.response;

import com.ssafy.tripon.reviewdetail.application.ReviewDetailServiceResponse;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewDetailSaveResponse(
        Integer id,
        Integer reviewId,
        Integer day,
        String content,
        List<Integer> attractions, // reviewdetailId로 조회한 관광지 목록
        List<String> pictures, // reviewdetailId로 조회한 후기 사진 목록
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewDetailSaveResponse from(ReviewDetailServiceResponse response) {
        return new ReviewDetailSaveResponse(
                response.id(),
                response.reviewId(),
                response.day(),
                response.content(),
                response.attractions(),
                response.pictures(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
