package com.ssafy.tripon.review.presentation.response;

import com.ssafy.tripon.review.application.ReviewServiceResponse;

public record ReviewUpdateResponse(
        String title
) {
    public static ReviewUpdateResponse from(ReviewServiceResponse response) {
        return new ReviewUpdateResponse(response.title());
    }
}
