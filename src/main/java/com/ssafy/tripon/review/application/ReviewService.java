package com.ssafy.tripon.review.application;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.tripon.comment.domain.CommentRepository;
import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.common.exception.ErrorCode;
import com.ssafy.tripon.common.utils.FileStorageService;
import com.ssafy.tripon.like.domain.LikeRepository;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.member.domain.MemberRepository;
import com.ssafy.tripon.review.application.command.ReviewSaveCommand;
import com.ssafy.tripon.review.application.command.ReviewUpdateCommand;
import com.ssafy.tripon.review.application.response.ReviewLikeCount;
import com.ssafy.tripon.review.application.response.ReviewLiked;
import com.ssafy.tripon.review.domain.Review;
import com.ssafy.tripon.review.domain.ReviewRepository;
import com.ssafy.tripon.review.presentation.response.PopularReviewResponse;
import com.ssafy.tripon.reviewattraction.domain.ReviewAttractionRepository;
import com.ssafy.tripon.reviewdetail.domain.ReviewDetailRepository;
import com.ssafy.tripon.reviewpicture.domain.ReviewPicture;
import com.ssafy.tripon.reviewpicture.domain.ReviewPictureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final LikeRepository likeRepository;
	private final ReviewDetailRepository reviewDetailRepository;
	private final MemberRepository memberRepository;
	private final CommentRepository commentRepository;
	private final ReviewAttractionRepository reviewAttractionRepository;
	private final FileStorageService fileStorageService;

	public Integer saveReview(ReviewSaveCommand command) {
		String storedUrl = null;
		Review review = null;
		
		if (command.thumbnail() != null) {
			storedUrl = fileStorageService.upload(command.thumbnail());
			review = command.toReview(command.thumbnail().getOriginalFilename(), storedUrl);
		}else {
			review = command.toReview();	
		}
		
		reviewRepository.save(review);
		return review.getId();
	}

	public List<ReviewServiceResponse> findAllReviews(String email) {
		// 1. 전체 리뷰 조회
		List<Review> reviews = reviewRepository.findAll();
		if (reviews.isEmpty())
			return List.of();

		// 2. 리뷰 ID 목록 추출
		List<Integer> reviewIds = reviews.stream().map(Review::getId).toList();

		// 3. 좋아요 수 조회 (List → Map 변환)
		List<ReviewLikeCount> likeCountList = likeRepository.countAllByReviewIds(reviewIds);

		likeCountList.forEach(dto -> {
			System.out.println("reviewId: " + dto.getReviewId() + ", cnt: " + dto.getCnt());
		});

		Map<Integer, Integer> likeCountMap = likeCountList.stream()
				.collect(Collectors.toMap(ReviewLikeCount::getReviewId, ReviewLikeCount::getCnt));

		// 4. 로그인 유저가 좋아요한 리뷰 조회 (List → Set 변환)
		List<ReviewLiked> likedList = likeRepository.findLikedReviewIdsByEmail(reviewIds, email);
		Set<Integer> likedReviewIds = likedList.stream().map(ReviewLiked::getReviewId).collect(Collectors.toSet());

		// 5. 조합하여 응답 DTO 생성
		return reviews.stream().map(review -> {
			Integer reviewId = review.getId();
			int likeCount = likeCountMap.getOrDefault(reviewId, 0);
			boolean liked = likedReviewIds.contains(reviewId);
			return ReviewServiceResponse.from(review, likeCount, liked);
		}).toList();
	}

	public ReviewServiceResponse findReview(Integer id, String email) {
		Review review = Optional.ofNullable(reviewRepository.findById(id))
				.orElseThrow(() -> new CustomException(ErrorCode.REVIEWS_NOT_FOUND));

		List<Integer> detailIds = reviewDetailRepository.findAllIdByReviewId(id);
		int likes = likeRepository.getCountByReviewId(id);
		boolean liked = likeRepository.existsByReviewIdAndEmail(id, email);
		Member member = memberRepository.findByEmail(review.getMemberEmail());
		return ReviewServiceResponse.from(review, detailIds, likes, liked, member.getName(),
				member.getProfileImageUrl());
	}

	public ReviewServiceResponse updateReview(ReviewUpdateCommand command) {
		// S3에서 기존 썸네일 파일 삭제
		String url = reviewRepository.findThumbNailByReviewId(command.id());

		if (url != null) {
			fileStorageService.delete(url);
			System.out.println("사진이 s3에서 삭제되었습니다.");
			url = null;
		}

		// S3에 저장
		if (command.thumbnail() != null) {
			url = fileStorageService.upload(command.thumbnail());
			System.out.println("사진이 s3에 저장되었습니다. " + url);
		}

		Review review = null;

		// 리뷰 전체 수정
		if (url != null) {
			review = command.toReview(url);
		} else {
			review = command.toReview();
		}

		 System.out.println(review);
		int result = reviewRepository.update(review);

		// 예외처리
		if (result == 0) {
			throw new CustomException(ErrorCode.REVIEWS_NOT_FOUND);
		}

		return ReviewServiceResponse.from(review);
	}

	public void deleteReview(Integer id) {
		List<Integer> detailIds = reviewDetailRepository.findAllIdByReviewId(id);

		// 일차별 후기 관광지 삭제
		for (Integer detailId : detailIds) {
			reviewAttractionRepository.deleteAllByReviewDetailId(detailId);
		}

		// 일차별 후기 삭제
		reviewDetailRepository.deleteAllByReviewId(id);

		// 좋아요 먼저 삭제
		likeRepository.deleteAllByReviewID(id);

		// 댓글 먼저 삭제
		commentRepository.deleteAllByReviewId(id);

		// 대표 사진 있으면 삭제
		String url = reviewRepository.findThumbNailByReviewId(id);
		if (url != null) {
			fileStorageService.delete(url);
		}

		int result = reviewRepository.deleteById(id);

		// 예외처리
		if (result == 0) {
			throw new CustomException(ErrorCode.REVIEWS_NOT_FOUND);
		}
	}

	public List<PopularReviewResponse> findPopularReview() {
		List<Review> topReviews = reviewRepository.findTop4ByLikeInLastMonth();

		return topReviews.stream().map(review -> {
			Integer reviewId = review.getId();
			Integer reviewDetailId = reviewDetailRepository.findIdByReviewIdAndDay(reviewId, 1);
			String content = reviewDetailRepository.findContentById(reviewDetailId);
			Member member = memberRepository.findByEmail(review.getMemberEmail());
			return new PopularReviewResponse(review.getId(), review.getTitle(), member.getName(),
					member.getProfileImageUrl(), content, review.getThumbnailUrl());
		}).toList();
	}

	public List<ReviewServiceResponse> findAllReviewsByMemberId(String email) {
		// 1. 전체 리뷰 조회
		List<Review> reviews = reviewRepository.findAllReviewsByMemberId(email);
		if (reviews.isEmpty())
			return List.of();

		// 2. 리뷰 ID 목록 추출
		List<Integer> reviewIds = reviews.stream().map(Review::getId).toList();

		// 3. 좋아요 수 조회 (List → Map 변환)
		List<ReviewLikeCount> likeCountList = likeRepository.countAllByReviewIds(reviewIds);
		Map<Integer, Integer> likeCountMap = likeCountList.stream()
				.collect(Collectors.toMap(ReviewLikeCount::getReviewId, ReviewLikeCount::getCnt));

		// 4. 로그인 유저가 좋아요한 리뷰 조회 (List → Set 변환)
		List<ReviewLiked> likedList = likeRepository.findLikedReviewIdsByEmail(reviewIds, email);
		Set<Integer> likedReviewIds = likedList.stream().map(ReviewLiked::getReviewId).collect(Collectors.toSet());

		// 5. 조합하여 응답 DTO 생성
		return reviews.stream().map(review -> {
			Integer reviewId = review.getId();
			int likeCount = likeCountMap.getOrDefault(reviewId, 0);
			boolean liked = likedReviewIds.contains(reviewId);
			return ReviewServiceResponse.from(review, likeCount, liked);
		}).toList();
	}

}
