package com.ssafy.tripon.review.application;

import com.ssafy.tripon.comment.domain.CommentRepository;
import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.common.exception.ErrorCode;
import com.ssafy.tripon.like.domain.LikeRepository;
import com.ssafy.tripon.member.domain.MemberRepository;
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
	private final LikeRepository likeRepository;
	private final ReviewDetailRepository reviewDetailRepository;
	private final MemberRepository memberRepository;
	private final CommentRepository commentRepository;
	
	public Integer saveReview(ReviewSaveCommand command) {
		Review review = command.toReview();
		reviewRepository.save(review);
		return review.getId();
	}

	public List<ReviewServiceResponse> findAllReviews(String email) {
		return reviewRepository.findAll(email).stream().map(review -> {
			int likes = likeRepository.getCountByReviewId(review.getId());
			boolean liked = likeRepository.existsByReviewIdAndEmail(review.getId(), email);

			return ReviewServiceResponse.from(review, likes, liked);
		}).toList();
	}

	public ReviewServiceResponse findReview(Integer id, String email) {
		Review review = Optional.ofNullable(reviewRepository.findById(id))
				.orElseThrow(() -> new CustomException(ErrorCode.REVIEWS_NOT_FOUND));

		List<Integer> detailIds = reviewDetailRepository.findAllIdByReviewId(id);
		int likes = likeRepository.getCountByReviewId(id);
		boolean liked = likeRepository.existsByReviewIdAndEmail(id, email);
		String memberName = memberRepository.findByEmail(review.getMemberEmail()).getName();
		return ReviewServiceResponse.from(review, detailIds, likes, liked, memberName);
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
		// 댓글 먼저 삭제
		commentRepository.deleteAllByReviewId(id);
		
		int result = reviewRepository.deleteById(id);

		// 예외처리
		if (result == 0) {
			throw new CustomException(ErrorCode.REVIEWS_NOT_FOUND);
		}
	}
}
