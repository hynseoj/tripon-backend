package com.ssafy.tripon.comment.application;

import java.time.LocalDateTime;

import com.ssafy.tripon.comment.domain.Comment;

public record CommentServiceResponse(
		Integer id,
		Integer parentId,
		String memberEmail,
		String memberName,
		String profileImageUrl,
		String content,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
		) {
	public static CommentServiceResponse from(Comment comment, String memberName, String profileImageUrl) {
		return new CommentServiceResponse(comment.getId(), comment.getParentId(), 
				comment.getMemberId(), memberName,profileImageUrl,comment.getContent(), comment.getCreatedAt(), 
				comment.getUpdatedAt());
	}
}
