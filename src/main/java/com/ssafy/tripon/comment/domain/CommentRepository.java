package com.ssafy.tripon.comment.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentRepository {

	// 생성
	void save(Comment comment);
	// 조회
	List<Comment> findByReviewId(Integer reviewId);
	// 수정
	int update(Comment comment);
	// 삭제
	int delete(Integer id);
	// 전체 삭제
	void deleteAllByReviewId(Integer reviewId);
}
