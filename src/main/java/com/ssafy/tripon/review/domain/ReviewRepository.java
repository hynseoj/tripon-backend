package com.ssafy.tripon.review.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewRepository {

	void save(Review review);

	List<Review> findAll();

	List<Review> findAllReviewsByMemberId(String email);

	Review findById(Integer id);

	int update(Review review);

	int updateWithThumbnail(Review review);

	int deleteById(Integer id);

	List<Review> findTop4ByLikeInLastMonth();

	String findThumbNailByReviewId(Integer reviewId);

	List<Review> findAllByKeyword(@Param("keyword") String keyword);

	// 페이지네이션
	List<Review> findPagedSorted(@Param("offset") int offset, @Param("size") int size, @Param("orderBy") String orderBy, @Param("keyword") String keyword);
	int countAll(String keyword);
	List<Review> findPagedByLikeCount(@Param("offset") int offset, @Param("size") int size, @Param("keyword") String keyword);

}
