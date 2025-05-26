package com.ssafy.tripon.like.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.tripon.review.application.response.ReviewLikeCount;
import com.ssafy.tripon.review.application.response.ReviewLiked;

@Mapper
public interface LikeRepository {

	void save(Like like);

	int delete(@Param("reviewId") Integer reviewId, @Param("email") String email);
	int deleteAllByReviewID(@Param("reviewId") Integer reviewId);
	List<ReviewLikeCount> countAllByReviewIds(@Param("reviewIds") List<Integer> reviewIds);

	List<ReviewLiked> findLikedReviewIdsByEmail(@Param("reviewIds") List<Integer> reviewIds,
			@Param("email") String email);

	// 단건 조회
	Integer getCountByReviewId(Integer reviewId);

	boolean existsByReviewIdAndEmail(Integer reviewId, String email);
}
