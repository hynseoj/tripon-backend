package com.ssafy.tripon.reviewdetail.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewDetailRepository {

	void save(ReviewDetail reviewDetail);

	List<Integer> findAllIdByReviewId(Integer reviewId);

	ReviewDetail findById(Integer id);

	int update(ReviewDetail reviewDetail);

	void deleteById(Integer id);

	void deleteAllByReviewId(Integer reviewId);

	List<ReviewDetail> findByReviewId(Integer reviewId);

	Map<Integer, Integer> findIdByReviewIdsAndDay(@Param("reviewIds") List<Integer> reviewIds, @Param("day") int day);

	String findContentById(Integer reviewDetailId);

	Integer findIdByReviewIdAndDay(@Param("reviewId") Integer reviewId, @Param("day") int day);
}
