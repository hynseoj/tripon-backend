package com.ssafy.tripon.comment.application;

import java.time.LocalDateTime;

import com.ssafy.tripon.comment.domain.Comment;

public record CommentServiceResponse(
		Integer id,
		Integer parentId,
		String memberId,
		String content,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
		) {
	public static CommentServiceResponse from(Comment comment) {
		return new CommentServiceResponse(comment.getId(), comment.getParentId(), 
				comment.getMemberId(),comment.getContent(), comment.getCreatedAt(), 
				comment.getUpdatedAt());
	}
}
