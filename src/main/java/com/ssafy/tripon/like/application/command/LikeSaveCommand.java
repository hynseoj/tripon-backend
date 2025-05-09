package com.ssafy.tripon.like.application.command;

import com.ssafy.tripon.like.domain.Like;

public record LikeSaveCommand(
        String email,
        Integer reviewId
) {
    public Like toLike() {
        return new Like(email, reviewId);
    }
}
