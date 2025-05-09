package com.ssafy.tripon.like.presentation.response;

public record LikeCountResponse(
        Integer likeCount
) {
    public static LikeCountResponse from(Integer likeCount) {
        return new LikeCountResponse(likeCount);
    }
}
