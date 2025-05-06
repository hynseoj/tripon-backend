package com.ssafy.tripon.review.application;

import com.ssafy.tripon.review.application.command.ReviewSaveCommand;
import com.ssafy.tripon.review.domain.Review;
import com.ssafy.tripon.review.domain.ReviewAttraction;
import com.ssafy.tripon.review.domain.ReviewAttractionRepository;
import com.ssafy.tripon.review.domain.ReviewDetail;
import com.ssafy.tripon.review.domain.ReviewDetailRepository;
import com.ssafy.tripon.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewDetailRepository reviewDetailRepository;
    private final ReviewAttractionRepository reviewAttractionRepository;

    public Integer saveReview(ReviewSaveCommand command) {
        // review 저장
        Review review = command.toReview();
        reviewRepository.save(review);
        Integer reviewId = review.getId();

                command.details().stream()
                .forEach(detail -> {
                    // review_detail 저장
                    ReviewDetail reviewDetail = detail.toReviewDetail(reviewId);
                    reviewDetailRepository.save(reviewDetail);
                    Integer reviewDetailId = reviewDetail.getId();

                    // review_attraction 저장
                    detail.attractions()
                            .forEach(attractionId -> {
                                ReviewAttraction reviewAttraction = new ReviewAttraction(reviewDetailId, attractionId);
                                reviewAttractionRepository.save(reviewAttraction);
                            });

                    // @Todo: picture 처리 & 저장 구현하기
                });

        return reviewId;
    }
}
