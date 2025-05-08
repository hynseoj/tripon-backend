package com.ssafy.tripon.review.presentation.request;

import com.ssafy.tripon.review.application.command.ReviewSaveCommand;

public record ReviewSaveRequest(
        String title
) {
    public ReviewSaveCommand toCommand(String email) {
        return new ReviewSaveCommand(email, title);
    }
}
