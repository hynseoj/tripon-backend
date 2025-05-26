package com.ssafy.tripon.review.presentation.response;

public record PopularReviewResponse(
	    Integer id,
	    String title,
	    String memberName,
	    String ProfileImageUrl,
	    String previewContent,
	    String thumbnailUrl
	) {

}

