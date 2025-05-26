package com.ssafy.tripon.review.application.command;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.tripon.review.domain.Review;

public record ReviewUpdateCommand(
        Integer id,
        String email,
        String title,
        MultipartFile thumbnail
) {
    public Review toReview(String thumbnailUrl) {
    	System.out.println("thumbnailUrl이다" + thumbnailUrl);
        return new Review(id, email, title, thumbnail.getOriginalFilename(), thumbnailUrl);
    }
    
    public Review toReview() {
    	System.out.println("thumbnailUrl아니다");
        return new Review(id, email, title, null, null);
    }
}