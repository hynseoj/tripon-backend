package com.ssafy.tripon.review.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Review {

    private Integer id;
    private String memberEmail;
    private String title;
    private String thumbnailOriginal;
    private String thumbnailUrl;    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Review(String memberEmail, String title) {
    	this.memberEmail = memberEmail;
        this.title = title;
    }
    
    public Review(Integer id, String memberEmail, String title, String thumbnailOriginal, String thumbnailUrl) {
        this.id = id;
    	this.memberEmail = memberEmail;
        this.title = title;
        this.thumbnailOriginal = thumbnailOriginal;
        this.thumbnailUrl = thumbnailUrl;
    }
    
	public Review(String memberEmail, String title, String thumbnailOriginal, String thumbnailUrl) {
		this.memberEmail = memberEmail;
        this.title = title;
        this.thumbnailOriginal = thumbnailOriginal;
        this.thumbnailUrl = thumbnailUrl;
    }
}
