package com.ssafy.tripon.review.domain;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewRepository {

    void save(Review review);
    List<Review> findAll(String email);
    Review findById(Integer id);
    int update(Review review);
    int deleteById(Integer id);
    List<Review> findTop4ByLikeInLastMonth();
}
