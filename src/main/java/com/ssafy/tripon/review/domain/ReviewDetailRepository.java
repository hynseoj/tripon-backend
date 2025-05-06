package com.ssafy.tripon.review.domain;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewDetailRepository {

    void save(ReviewDetail reviewDetail);
    List<ReviewDetail> findAll();
    ReviewDetail findById(Integer id);
    void update(ReviewDetail reviewDetail);
    void deleteById(Integer id);
}
