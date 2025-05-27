package com.ssafy.tripon.review.presentation;

import com.ssafy.tripon.common.auth.config.LoginMember;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.review.application.ReviewService;
import com.ssafy.tripon.review.application.ReviewServiceResponse;
import com.ssafy.tripon.review.presentation.request.ReviewSaveRequest;
import com.ssafy.tripon.review.presentation.request.ReviewUpdateRequest;
import com.ssafy.tripon.review.presentation.response.PopularReviewResponse;
import com.ssafy.tripon.review.presentation.response.ReviewFindAllResponse;
import com.ssafy.tripon.review.presentation.response.ReviewFindResponse;
import com.ssafy.tripon.review.presentation.response.ReviewPageResponse;
import com.ssafy.tripon.review.presentation.response.ReviewUpdateResponse;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping
	public ResponseEntity<Void> saveReview(@Valid @RequestPart ReviewSaveRequest request,
			@Valid @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
			@LoginMember Member member) {

		Integer id = reviewService.saveReview(request.toCommand(member.getEmail(), thumbnail));
		return ResponseEntity.created(URI.create("/api/v1/reviews/" + id)).build();
	}

//	@GetMapping
//	public ResponseEntity<ReviewFindAllResponse> findAllReviews(
//	        @LoginMember Member member,
//	        @RequestParam(value = "keyword", required = false) String keyword
//	) {
//	    List<ReviewServiceResponse> responses = reviewService.findAllReviews(member.getEmail(), keyword);
//	    return ResponseEntity.ok(ReviewFindAllResponse.from(responses));
//	}

	@GetMapping
	public ResponseEntity<ReviewPageResponse> findAllReviews(@LoginMember Member member,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "sort", defaultValue = "latest") String sort) {
		return ResponseEntity.ok(reviewService.findPagedReviews(member.getEmail(), page, size, sort, keyword));
	}

	@GetMapping("/me")
	public ResponseEntity<ReviewFindAllResponse> findAllReviewsByMemberId(@LoginMember Member member) {
		List<ReviewServiceResponse> responses = reviewService.findAllReviewsByMemberId(member.getEmail());
		return ResponseEntity.ok(ReviewFindAllResponse.from(responses));
	}

	@GetMapping("/{reviewId}")
	public ResponseEntity<ReviewFindResponse> findReview(@PathVariable(value = "reviewId") Integer id,
			@LoginMember Member member) {
		ReviewServiceResponse response = reviewService.findReview(id, member.getEmail());
		System.out.println(response);
		return ResponseEntity.ok(ReviewFindResponse.from(response));
	}

	@PutMapping("/{reviewId}")
	public ResponseEntity<ReviewUpdateResponse> updateReview(@PathVariable(value = "reviewId") Integer id,
			@Valid @RequestPart("request") ReviewUpdateRequest request,
			@RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
			@RequestPart(value = "thumbnailUrl", required = false) String thumbnailUrl, @LoginMember Member member) {
		System.out.println("업데이트 썸네일: " + thumbnail + " " + thumbnailUrl);
		ReviewServiceResponse response = reviewService
				.updateReview(request.toCommand(id, member.getEmail(), thumbnail, thumbnailUrl));

		return ResponseEntity.ok(ReviewUpdateResponse.from(response));
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<Void> deleteReview(@PathVariable(value = "reviewId") Integer id) {
		System.out.println("삭제!");
		reviewService.deleteReview(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/popular")
	public ResponseEntity<List<PopularReviewResponse>> findPopularReview() {
		List<PopularReviewResponse> result = reviewService.findPopularReview();
		return ResponseEntity.ok(result);
	}

}
