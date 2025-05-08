package com.ssafy.tripon.reviewpicture.domain;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewPictureRepository {

    void save(ReviewPicture reviewPicture);
    List<ReviewPicture> findAll();
    ReviewPicture findById(Integer id);
    void update(ReviewPicture reviewPicture);
    void deleteById(Integer id);
}
