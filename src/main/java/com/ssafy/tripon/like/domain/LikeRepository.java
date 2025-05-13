package com.ssafy.tripon.like.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeRepository {

    void save(Like like);
    Integer getCountByReviewId(Integer reviewId);
    int delete(@Param("reviewId") Integer reviewId, @Param("email") String email);
}
