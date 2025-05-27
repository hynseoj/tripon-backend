package com.ssafy.tripon.plan.presentation.response;

public record LinkCreateResponse(
        String url
) {
    public static LinkCreateResponse from(String url) {
        return new LinkCreateResponse(url);
    }
}
