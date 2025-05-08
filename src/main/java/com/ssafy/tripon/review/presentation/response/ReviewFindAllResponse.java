package com.ssafy.tripon.review.presentation.response;

import com.ssafy.tripon.review.application.ReviewServiceResponse;
import java.util.List;

public record ReviewFindAllResponse(
        List<ReviewFindResponse> reviews
) {
    public static ReviewFindAllResponse from(List<ReviewServiceResponse> responses) {
        return new ReviewFindAllResponse(
          responses.stream()
                  .map(ReviewFindResponse::from)
                  .toList()
        );
    }
}
