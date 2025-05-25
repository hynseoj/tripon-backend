package com.ssafy.tripon.comment.presentation.response;

import java.time.LocalDateTime;

import com.ssafy.tripon.comment.application.CommentServiceResponse;

public record CommentFindResponse(
		Integer id,
		String content,
		Integer parentId,
		String memberEmail,
		String memberName,
		LocalDateTime createdAt,
        LocalDateTime updatedAt
		) {
	public static CommentFindResponse from(CommentServiceResponse response) {
		return new CommentFindResponse(response.id(), response.content(),
				response.parentId(), response.memberEmail(), response.memberName(), response.createdAt(),
				response.updatedAt()
				);
	}
}
