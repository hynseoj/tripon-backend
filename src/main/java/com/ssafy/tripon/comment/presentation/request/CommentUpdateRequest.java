package com.ssafy.tripon.comment.presentation.request;

import com.ssafy.tripon.comment.application.command.CommentUpdateCommand;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest(
		@NotBlank(message = "댓글 내용을 입력해주세요.")
		String content,
		Integer parentId
		) {
	public CommentUpdateCommand toCommand(Integer reviewId, Integer id) {
        return new CommentUpdateCommand(id, content, parentId, reviewId);
    }
}
