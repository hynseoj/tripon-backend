package com.ssafy.tripon.review.application;

import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.common.exception.ErrorCode;
import com.ssafy.tripon.review.application.command.ReviewSaveCommand;
import com.ssafy.tripon.review.application.command.ReviewUpdateCommand;
import com.ssafy.tripon.review.domain.Review;
import com.ssafy.tripon.review.domain.ReviewRepository;
import com.ssafy.tripon.reviewdetail.domain.ReviewDetailRepository;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ReviewDetailRepository reviewDetailRepository;

	public Integer saveReview(ReviewSaveCommand command) {
		Review review = command.toReview();
		reviewRepository.save(review);
		return review.getId();
	}

	public List<ReviewServiceResponse> findAllReviews() {
		return reviewRepository.findAll().stream().map(ReviewServiceResponse::from).toList();
	}

	public ReviewServiceResponse findReview(Integer id) {
		Review review = Optional.ofNullable(reviewRepository.findById(id))
				.orElseThrow(() -> new CustomException(ErrorCode.REVIEWS_NOT_FOUND));

		return ReviewServiceResponse.from(review, reviewDetailRepository.findAllIdByReviewId(id));
	}

	public ReviewServiceResponse updateReview(ReviewUpdateCommand command) {
		Review review = command.toReview();
		int result = reviewRepository.update(review);

		// 예외처리
		if (result == 0) {
			throw new CustomException(ErrorCode.REVIEWS_NOT_FOUND);
		}

		return ReviewServiceResponse.from(review);
	}

	public void deleteReview(Integer id) {
		int result = reviewRepository.deleteById(id);

		// 예외처리
		if (result == 0) {
			throw new CustomException(ErrorCode.REVIEWS_NOT_FOUND);
		}
	}
}
