package com.ssafy.tripon.reviewpicture.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewPictureRepository {

    void save(ReviewPicture reviewPicture);
    List<ReviewPicture> findAllByReviewDetailId(Integer reviewDetailId);
    void deleteAllByReviewDetailId(Integer reviewDetailId);
    void delete(Integer id);
    String findFirstUrlByReviewId(Integer detailId);
    Map<Integer, String> findFirstUrlsByDetailIds(@Param("detailIds") List<Integer> detailIds);
}
