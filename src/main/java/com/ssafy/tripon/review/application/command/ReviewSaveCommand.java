package com.ssafy.tripon.review.application.command;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.tripon.review.domain.Review;

public record ReviewSaveCommand(
        String email,
        String title,
        MultipartFile thumbnail
) {
    public Review toReview(String thumbnailOriginal, String thumbnailUrl) {
        return new Review(email, title, thumbnailOriginal, thumbnailUrl);
    }
}
