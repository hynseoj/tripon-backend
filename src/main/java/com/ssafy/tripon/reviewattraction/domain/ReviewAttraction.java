package com.ssafy.tripon.reviewattraction.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewAttraction {

    private Integer id;
    private Integer reviewDetailId;
    private Integer attractionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReviewAttraction(Integer reviewDetailId, Integer attractionId) {
        this.reviewDetailId = reviewDetailId;
        this.attractionId = attractionId;
    }
}
