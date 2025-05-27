package com.ssafy.tripon.review.presentation.response;

import java.util.List;

import com.ssafy.tripon.review.application.ReviewServiceResponse;
import com.ssafy.tripon.review.domain.Review;

public record ReviewPageResponse(
        List<ReviewServiceResponse> reviews,
        PageInfo pageInfo
) {
    public static ReviewPageResponse from(List<Review> reviews, int page, int size, int totalCount) {
        List<ReviewServiceResponse> responseList = reviews.stream()
                .map(ReviewServiceResponse::from)
                .toList();

        int totalPages = (int) Math.ceil((double) totalCount / size);
        return new ReviewPageResponse(responseList, new PageInfo(page, totalPages, totalCount));
    }

    public record PageInfo(int currentPage, int totalPages, int totalElements) {}
}
