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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping
	public ResponseEntity<Void> saveReview(@Valid @RequestBody ReviewSaveRequest request, @LoginMember Member member) {
		Integer id = reviewService.saveReview(request.toCommand(member.getEmail())); // @Todo: 로그인 구현 후 수정
		return ResponseEntity.created(URI.create("/api/v1/reviews/" + id)).build();
	}

	@GetMapping
	public ResponseEntity<ReviewFindAllResponse> findAllReviews(@LoginMember Member member) {
		List<ReviewServiceResponse> responses = reviewService.findAllReviews(member.getEmail());
		return ResponseEntity.ok(ReviewFindAllResponse.from(responses));
	}

	@GetMapping("/{reviewId}")
	public ResponseEntity<ReviewFindResponse> findReview(@PathVariable(value = "reviewId") Integer id,
			@LoginMember Member member) {
		ReviewServiceResponse response = reviewService.findReview(id, member.getEmail());
		return ResponseEntity.ok(ReviewFindResponse.from(response));
	}

	@PutMapping("/{reviewId}")
	public ResponseEntity<ReviewUpdateResponse> updateReview(@PathVariable(value = "reviewId") Integer id,
			@Valid @RequestBody ReviewUpdateRequest request, @LoginMember Member member) {
		System.out.println(id);
		ReviewServiceResponse response = reviewService.updateReview(request.toCommand(id, member.getEmail()));
		return ResponseEntity.ok(ReviewUpdateResponse.from(response)); // @Todo: 로그인 구현 후 수정
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<Void> deleteReview(@PathVariable(value = "reviewId") Integer id) {
		reviewService.deleteReview(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/popular")
	public ResponseEntity<List<PopularReviewResponse>> findPopularReview() {
	    List<PopularReviewResponse> result = reviewService.findPopularReview();
	    System.out.println(result.get(0));
	    return ResponseEntity.ok(result);
	}

}
