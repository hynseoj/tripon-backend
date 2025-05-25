package com.ssafy.tripon.comment.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.tripon.comment.application.command.CommentSaveCommand;
import com.ssafy.tripon.comment.application.command.CommentUpdateCommand;
import com.ssafy.tripon.comment.domain.Comment;
import com.ssafy.tripon.comment.domain.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
	private final CommentRepository commentRepository;

	// 댓글 생성
	public void saveComment(CommentSaveCommand command) {
		commentRepository.save(command.toComment());
	}

	// 댓글 조회
	public List<CommentServiceResponse> findAllByReviewId(Integer reviewId) {
		return commentRepository.findByReviewId(reviewId).stream().map(CommentServiceResponse::from).toList();
	}

	// 댓글 수정
	public void updateComment(CommentUpdateCommand command) {
		Comment bar = command.toComment();
		commentRepository.update(command.toComment());
	}

	// 댓글 삭제
	public void deleteComment(Integer id) {
		commentRepository.delete(id);
	}
}
