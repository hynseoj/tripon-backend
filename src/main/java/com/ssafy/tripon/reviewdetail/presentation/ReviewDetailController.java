package com.ssafy.tripon.reviewdetail.presentation;

import com.ssafy.tripon.reviewdetail.application.ReviewDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews/{reviewId}")
@RequiredArgsConstructor
public class ReviewDetailController {

    private final ReviewDetailService reviewDetailService;

    @PostMapping
    public ResponseEntity<Void> saveReviewDetail(@PathVariable(value = "reviewId") Integer reviewId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{reviewDetailId}")
    public ResponseEntity<Void> findReviewDetail(@PathVariable(value = "reviewDetailId") Integer reviewDetailId) {
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reviewDetailId}")
    public ResponseEntity<Void> updateReviewDetail( @PathVariable(value = "reviewDetailId") Integer reviewDetailId) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reviewDetailId}")
    public ResponseEntity<Void> deleteReviewDetail(@PathVariable(value = "reviewDetailId") Integer reviewDetailId) {
        return ResponseEntity.noContent().build();
    }
}
