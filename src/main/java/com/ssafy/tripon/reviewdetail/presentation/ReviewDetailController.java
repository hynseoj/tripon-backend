package com.ssafy.tripon.reviewdetail.presentation;

import com.ssafy.tripon.reviewdetail.application.ReviewDetailService;
import com.ssafy.tripon.reviewdetail.application.ReviewDetailServiceResponse;
import com.ssafy.tripon.reviewdetail.presentation.request.ReviewDetailSaveRequest;
import com.ssafy.tripon.reviewdetail.presentation.request.ReviewDetailUpdateRequest;
import com.ssafy.tripon.reviewdetail.presentation.response.ReviewDetailFindResponse;
import com.ssafy.tripon.reviewdetail.presentation.response.ReviewDetailSaveResponse;
import com.ssafy.tripon.reviewdetail.presentation.response.ReviewDetailUpdateResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/reviews/{reviewId}")
@RequiredArgsConstructor
public class ReviewDetailController {

    private final ReviewDetailService reviewDetailService;

    @PostMapping
    public ResponseEntity<ReviewDetailSaveResponse> saveReviewDetail(
            @PathVariable(value = "reviewId") Integer reviewId,
            @RequestPart(value = "reviewDetail") ReviewDetailSaveRequest request,
            @RequestPart(value = "images") List<MultipartFile> pictures
    ) {
        ReviewDetailServiceResponse response = reviewDetailService.saveReviewDetail(
                request.toCommand(reviewId), pictures);
        return ResponseEntity.created(URI.create("/api/v1/reviews/" + reviewId + "/" + response.id()))
                .body(ReviewDetailSaveResponse.from(response));
    }

    @GetMapping("/{reviewDetailId}")
    public ResponseEntity<ReviewDetailFindResponse> findReviewDetail(@PathVariable(value = "reviewDetailId") Integer reviewDetailId) {
        ReviewDetailServiceResponse response = reviewDetailService.findReviewDetail(reviewDetailId);
        return ResponseEntity.ok(ReviewDetailFindResponse.from(response));
    }

    @PutMapping("/{reviewDetailId}")
    public ResponseEntity<ReviewDetailUpdateResponse> updateReviewDetail(
            @PathVariable(value = "reviewId") Integer reviewId,
            @PathVariable(value = "reviewDetailId") Integer reviewDetailId,
            @RequestPart(value = "reviewDetail") ReviewDetailUpdateRequest request,
            @RequestPart(value = "images") List<MultipartFile> pictures
    ) {
        ReviewDetailServiceResponse response = reviewDetailService.updateReviewDetail(request.toCommand(reviewDetailId, reviewId), pictures);
        return ResponseEntity.ok(ReviewDetailUpdateResponse.from(response));
    }

    @DeleteMapping("/{reviewDetailId}")
    public ResponseEntity<Void> deleteReviewDetail(@PathVariable(value = "reviewDetailId") Integer reviewDetailId) {
        reviewDetailService.deleteReviewDetailById(reviewDetailId);
        return ResponseEntity.noContent().build();
    }
}
