package com.ssafy.tripon.comment.application;

import com.ssafy.tripon.comment.application.command.CommentSaveCommand;
import com.ssafy.tripon.comment.application.command.CommentUpdateCommand;
import com.ssafy.tripon.comment.domain.Comment;
import com.ssafy.tripon.comment.domain.CommentRepository;
import com.ssafy.tripon.review.application.ReviewService;
import com.ssafy.tripon.review.application.command.ReviewSaveCommand;
import com.ssafy.tripon.review.domain.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CommentServiceTest {

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ReviewRepository reviewRepository;

	@Test
	void 댓글을_저장할_수_있다() {
		// given
		Integer reviewId = reviewService.saveReview(new ReviewSaveCommand("admin@ssafy.com", "리뷰 제목"));
		CommentSaveCommand command = new CommentSaveCommand("댓글 내용", null, "admin@ssafy.com", reviewId);

		// when
		commentService.saveComment(command);

		// then
		List<Comment> comments = commentRepository.findByReviewId(reviewId);
		assertThat(comments).hasSize(1);
		assertThat(comments.get(0).getContent()).isEqualTo("댓글 내용");
	}

	@Test
	void 댓글을_조회할_수_있다() {
		// given
		Integer reviewId = reviewService.saveReview(new ReviewSaveCommand("admin@ssafy.com", "리뷰 제목"));
		System.out.println(reviewId);
		commentService.saveComment(new CommentSaveCommand("댓글1", null, "admin@ssafy.com", reviewId));
		commentService.saveComment(new CommentSaveCommand("댓글2", null, "admin@ssafy.com", reviewId));

		// when
		List<CommentServiceResponse> response = commentService.findAllByReviewId(reviewId);

		// then
		assertThat(response).hasSize(2);
		assertThat(response.get(0).content()).isEqualTo("댓글1");
		assertThat(response.get(1).content()).isEqualTo("댓글2");
	}

	@Test
	void 댓글을_수정할_수_있다() {
		// given
		Integer reviewId = reviewService.saveReview(new ReviewSaveCommand("admin@ssafy.com", "리뷰 제목"));
		commentService.saveComment(new CommentSaveCommand("원본 댓글", null, "admin@ssafy.com", reviewId));
		Integer commentId = commentRepository.findByReviewId(reviewId).get(0).getId();
		System.out.println(commentId);
		// when
		commentService.updateComment(new CommentUpdateCommand(commentId, "수정된 댓글", null, reviewId));

		// then
		Comment updatedComment = commentRepository.findByReviewId(reviewId).get(0);
		assertThat(updatedComment.getContent()).isEqualTo("수정된 댓글");
	}

	@Test
	void 댓글을_삭제할_수_있다() {
		// given
		Integer reviewId = reviewService.saveReview(new ReviewSaveCommand("admin@ssafy.com", "리뷰 제목"));
		commentService.saveComment(new CommentSaveCommand("삭제할 댓글", null, "admin@ssafy.com", reviewId));
		Integer commentId = commentRepository.findByReviewId(reviewId).get(0).getId();

		// when
		commentService.deleteComment(commentId);

		// then
		List<Comment> comments = commentRepository.findByReviewId(reviewId);
		assertThat(comments).isEmpty();
	}
}
