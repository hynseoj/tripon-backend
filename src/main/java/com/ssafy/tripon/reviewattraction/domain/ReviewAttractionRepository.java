package com.ssafy.tripon.reviewattraction.domain;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewAttractionRepository {

    void save(ReviewAttraction reviewAttraction);
    List<ReviewAttraction> findAll();
    ReviewAttraction findById(Integer id);
    void update(ReviewAttraction reviewAttraction);
    void deleteById(Integer id);
}
