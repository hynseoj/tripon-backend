package com.ssafy.tripon.comment.presentation.response;

import java.util.List;

public record CommentFindAllByReviewIdResponse(
		List<CommentFindResponse> comments
		) {

}
