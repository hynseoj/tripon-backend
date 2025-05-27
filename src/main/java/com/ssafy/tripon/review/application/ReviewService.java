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
import com.ssafy.tripon.review.presentation.response.ReviewPageResponse;
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
	private final ReviewPictureRepository reviewPictureRepository;

	public Integer saveReview(ReviewSaveCommand command) {
		String storedUrl = null;
		Review review = null;

		if (command.thumbnail() != null) {
			storedUrl = fileStorageService.upload(command.thumbnail());
			review = command.toReview(command.thumbnail().getOriginalFilename(), storedUrl);
		} else {
			review = command.toReview();
		}

		reviewRepository.save(review);
		return review.getId();
	}

//	public List<ReviewServiceResponse> findAllReviews(String email, String keyword) {
//	    // 1. 리뷰 조회 (keyword 적용)
//	    List<Review> reviews = (keyword == null || keyword.isBlank())
//	            ? reviewRepository.findAll()
//	            : reviewRepository.findAllByKeyword(keyword); // ✅ 새 메서드
//
//	    if (reviews.isEmpty()) return List.of();
//
//	    // 2. 리뷰 ID 목록
//	    List<Integer> reviewIds = reviews.stream().map(Review::getId).toList();
//
//	    // 3. 좋아요 수 조회
//	    List<ReviewLikeCount> likeCountList = likeRepository.countAllByReviewIds(reviewIds);
//	    Map<Integer, Integer> likeCountMap = likeCountList.stream()
//	            .collect(Collectors.toMap(ReviewLikeCount::getReviewId, ReviewLikeCount::getCnt));
//
//	    // 4. 로그인 유저가 좋아요한 리뷰 ID 목록
//	    List<ReviewLiked> likedList = likeRepository.findLikedReviewIdsByEmail(reviewIds, email);
//	    Set<Integer> likedReviewIds = likedList.stream().map(ReviewLiked::getReviewId).collect(Collectors.toSet());
//
//	    // 5. 응답 DTO 조립
//	    return reviews.stream().map(review -> {
//	        Integer reviewId = review.getId();
//	        int likeCount = likeCountMap.getOrDefault(reviewId, 0);
//	        boolean liked = likedReviewIds.contains(reviewId);
//	        return ReviewServiceResponse.from(review, likeCount, liked);
//	    }).toList();
//	}
	public ReviewPageResponse findPagedReviews(String email, int page, int size, String sort, String keyword) {
	    int offset = page * size;

	    List<Review> reviews;
	    int totalCount = reviewRepository.countAll(keyword);

	    // ✅ 정렬 기준 분기 처리
	    if ("trend".equals(sort)) {
	        reviews = reviewRepository.findPagedByLikeCount(offset, size, keyword); // ← 좋아요 기반 정렬 쿼리
	    } else {
	        reviews = reviewRepository.findPagedSorted(offset, size, "created_at DESC", keyword);
	    }

	    if (reviews.isEmpty()) {
	        return new ReviewPageResponse(List.of(), new ReviewPageResponse.PageInfo(page, 0, 0));
	    }

	    // ✅ 리뷰 ID 목록 추출
	    List<Integer> reviewIds = reviews.stream().map(Review::getId).toList();

	    // ✅ 좋아요 수
	    Map<Integer, Integer> likeCountMap = likeRepository.countAllByReviewIds(reviewIds).stream()
	        .collect(Collectors.toMap(ReviewLikeCount::getReviewId, ReviewLikeCount::getCnt));

	    // ✅ 로그인 사용자의 좋아요 여부
	    Set<Integer> likedReviewIds = likeRepository.findLikedReviewIdsByEmail(reviewIds, email).stream()
	        .map(ReviewLiked::getReviewId)
	        .collect(Collectors.toSet());

	    // ✅ 응답 DTO 조립
	    List<ReviewServiceResponse> responseList = reviews.stream().map(review -> {
	        int reviewId = review.getId();
	        int likeCount = likeCountMap.getOrDefault(reviewId, 0);
	        boolean liked = likedReviewIds.contains(reviewId);
	        return ReviewServiceResponse.from(review, likeCount, liked);
	    }).toList();

	    int totalPages = (int) Math.ceil((double) totalCount / size);

	    return new ReviewPageResponse(responseList, new ReviewPageResponse.PageInfo(page, totalPages, totalCount));
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
		String url = command.thumbnailUrl();
		if (url == null) {
			// S3에서 기존 썸네일 파일 삭제
			url = reviewRepository.findThumbNailByReviewId(command.id());

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
		}

		Review review = null;

		// 리뷰 전체 수정
		int result = 0;
		// 기존 사진 쓰는 경우
		if(url != null && command.thumbnail() == null) {
			review = command.toReview();
			result = reviewRepository.update(review);
		}else {
			// 새 사진 쓰는경우
			if (url != null) {
				review = command.toReview(url);
			} else {
				review = command.toReview();
			}
			result = reviewRepository.updateWithThumbnail(review);
		}

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

		// S3에서 파일 삭제
		List<ReviewPicture> reviewPictures = reviewPictureRepository.findAllByReviewDetailId(id);
		for (ReviewPicture reviewPicture : reviewPictures) {
			fileStorageService.delete(reviewPicture.getUrl());
		}
		// DB 파일 삭제
		reviewPictureRepository.deleteAllByReviewDetailId(id);

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
