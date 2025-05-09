package com.ssafy.tripon.like.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    private Integer id;
    private String email;
    private Integer reviewId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Like(String email, Integer reviewId) {
        this.email = email;
        this.reviewId = reviewId;
    }
}
