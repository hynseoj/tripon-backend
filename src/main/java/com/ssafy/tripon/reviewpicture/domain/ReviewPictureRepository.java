package com.ssafy.tripon.reviewpicture.domain;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewPictureRepository {

    void save(ReviewPicture reviewPicture);
    List<ReviewPicture> findAllByReviewDetailId(Integer reviewDetailId);
    void deleteAllByReviewDetailId(Integer reviewDetailId);
}
