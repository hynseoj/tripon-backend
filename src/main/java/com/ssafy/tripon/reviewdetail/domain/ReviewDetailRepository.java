package com.ssafy.tripon.reviewdetail.domain;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewDetailRepository {

    void save(ReviewDetail reviewDetail);
    List<Integer> findAllIdByReviewId(Integer reviewId);
    
    ReviewDetail findById(Integer id);
    int update(ReviewDetail reviewDetail);
    void deleteById(Integer id);
    void deleteAllByReviewId(Integer reviewId);

	List<ReviewDetail> findByReviewId(Integer reviewId);
	
}
