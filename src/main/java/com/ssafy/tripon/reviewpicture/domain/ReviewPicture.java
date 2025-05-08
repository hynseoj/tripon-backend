package com.ssafy.tripon.reviewpicture.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewPicture {

    private Integer id;
    private Integer reviewDetailId;
    private String originalName;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReviewPicture(Integer reviewDetailId, String originalName, String url) {
        this.reviewDetailId = reviewDetailId;
        this.originalName = originalName;
        this.url = url;
    }
}
