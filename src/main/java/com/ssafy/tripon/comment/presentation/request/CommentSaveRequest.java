package com.ssafy.tripon.comment.presentation.request;

import com.ssafy.tripon.comment.application.command.CommentSaveCommand;

import jakarta.validation.constraints.NotBlank;

public record CommentSaveRequest(
		@NotBlank(message = "댓글 내용을 입력해주세요.")
		String content,
		Integer parentId
		) {
	public CommentSaveCommand toCommand(String email, Integer reviewId) {
        return new CommentSaveCommand(content, parentId, email, reviewId);
    }
}
