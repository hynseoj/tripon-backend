package com.ssafy.tripon.review.presentation;

import com.ssafy.tripon.review.application.ReviewService;
import com.ssafy.tripon.review.presentation.request.ReviewSaveRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReviewSaveRequest request) {
        Integer id = reviewService.saveReview(request.toCommand());
        return ResponseEntity.created(URI.create("/api/v1/reviews" + id)).build();
    }
}
