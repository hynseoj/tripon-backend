package com.ssafy.tripon.review.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    private Integer id;
    private String memberEmail;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Review(String memberEmail, String title) {
    	this.memberEmail = memberEmail;
        this.title = title;
    }
    
    public Review(Integer id, String memberEmail, String title) {
        this.id = id;
    	this.memberEmail = memberEmail;
        this.title = title;
    }
}
