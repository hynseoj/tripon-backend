package com.ssafy.tripon.reviewdetail.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDetail {

    private Integer id;
    private Integer reviewId;
    private Integer day;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReviewDetail(Integer reviewId, Integer day, String content) {
        this.reviewId = reviewId;
        this.day = day;
        this.content = content;
    }

    public ReviewDetail(Integer id, Integer reviewId, Integer day, String content) {
        this.id = id;
        this.reviewId = reviewId;
        this.day = day;
        this.content = content;
    }
}
