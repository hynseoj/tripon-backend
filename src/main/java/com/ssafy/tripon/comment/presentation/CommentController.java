package com.ssafy.tripon.comment.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tripon.comment.application.CommentService;
import com.ssafy.tripon.comment.application.CommentServiceResponse;
import com.ssafy.tripon.comment.presentation.request.CommentSaveRequest;
import com.ssafy.tripon.comment.presentation.request.CommentUpdateRequest;
import com.ssafy.tripon.comment.presentation.response.CommentFindAllByReviewIdResponse;
import com.ssafy.tripon.comment.presentation.response.CommentFindResponse;
import com.ssafy.tripon.common.auth.config.LoginMember;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.review.presentation.response.ReviewUpdateResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reviews/{reviewId}/comments")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> saveComment(@PathVariable(value = "reviewId") Integer reviewId,
			@RequestBody @Valid CommentSaveRequest request, @LoginMember Member member) {
		commentService.saveComment(request.toCommand(member.getEmail(), reviewId));
		return ResponseEntity.created(URI.create("/api/v1/reviews/" + reviewId + "/comments")).build();
	}

	@GetMapping
	public ResponseEntity<CommentFindAllByReviewIdResponse> findAllCommentByReviewId(
			@PathVariable(value = "reviewId") Integer reviewId) {
		CommentFindAllByReviewIdResponse response = new CommentFindAllByReviewIdResponse(
				commentService.findAllByReviewId(reviewId).stream().map(CommentFindResponse::from).toList());

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<Void> updateComment(
			@PathVariable Integer reviewId,
	        @PathVariable Integer commentId,
	        @RequestBody @Valid CommentUpdateRequest request
	) {
	    commentService.updateComment(request.toCommand(reviewId, commentId));
	    return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(
	        @PathVariable Integer commentId
	) {
	    commentService.deleteComment(commentId);
	    return ResponseEntity.ok().build();
	}
}
