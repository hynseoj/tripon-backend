package com.ssafy.tripon.review.application.command;

import com.ssafy.tripon.review.domain.Review;

public record ReviewUpdateCommand(
        Integer id,
        String email,
        String title
) {
    public Review toReview() {
        return new Review(email, title);
    }
}