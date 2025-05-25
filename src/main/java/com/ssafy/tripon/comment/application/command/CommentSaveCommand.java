package com.ssafy.tripon.comment.application.command;

import com.ssafy.tripon.comment.domain.Comment;

public record CommentSaveCommand(
		String content, 
		Integer parentId, 
		String email, 
		Integer reviewId
		) {
	public Comment toComment() {
		return new Comment(reviewId, parentId, email, content);
	}
}
