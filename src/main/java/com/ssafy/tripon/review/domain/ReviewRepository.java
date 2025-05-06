package com.ssafy.tripon.review.domain;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewRepository {

    void save(Review review);
    List<Review> findAll();
    Review findById(Integer id);
    void update(Review review);
    void deleteById(Integer id);
}
