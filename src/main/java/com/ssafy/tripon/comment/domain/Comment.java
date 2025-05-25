package com.ssafy.tripon.comment.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
	private Integer id;
	private Integer reviewId;
	private Integer parentId;
	private String memberId;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Comment(Integer reviewId, Integer parentId, String memberId, String content) {
		this.reviewId = reviewId;
		this.parentId = parentId;
		this.memberId = memberId;
		this.content = content;
	}

	public Comment(Integer id, Integer reviewId, Integer parentId, String memberId, String content) {
		this.id = id;
		this.reviewId = reviewId;
		this.parentId = parentId;
		this.memberId = memberId;
		this.content = content;
	}
}
