package com.ssafy.tripon.review.presentation.request;

import com.ssafy.tripon.review.application.command.ReviewUpdateCommand;

public record ReviewUpdateRequest(
        String title
) {
    public ReviewUpdateCommand toCommand(Integer id, String email) {
        return new ReviewUpdateCommand(id, email, title);
    }
}