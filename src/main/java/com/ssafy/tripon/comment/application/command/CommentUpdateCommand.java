package com.ssafy.tripon.comment.application.command;

import com.ssafy.tripon.comment.domain.Comment;

public record CommentUpdateCommand(
		Integer id,
		String content, 
		Integer parentId, 
		Integer reviewId
		) {
	public Comment toComment() {
		return new Comment(id, reviewId, parentId, null, content);
	}
}
