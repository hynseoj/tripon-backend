package com.ssafy.tripon.reviewdetail.presentation.response;

import com.ssafy.tripon.attraction.presentation.response.AttractionFindResponse;
import com.ssafy.tripon.reviewdetail.application.ReviewDetailServiceResponse;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewDetailUpdateResponse(
        Integer id,
        Integer reviewId,
        Integer day,
        String content,
        List<AttractionFindResponse> attractions, // reviewdetailId로 조회한 관광지 목록
        List<String> pictures, // reviewdetailId로 조회한 후기 사진 목록
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewDetailUpdateResponse from(ReviewDetailServiceResponse response) {
        return new ReviewDetailUpdateResponse(
                response.id(),
                response.reviewId(),
                response.day(),
                response.content(),
                response.attractions().stream().map(AttractionFindResponse::from).toList(),
                response.pictures(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
