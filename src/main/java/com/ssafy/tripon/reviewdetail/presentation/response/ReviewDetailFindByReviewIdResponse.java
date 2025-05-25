package com.ssafy.tripon.reviewdetail.presentation.response;

import java.util.List;

public record ReviewDetailFindByReviewIdResponse(
		List<ReviewDetailFindResponse> reviewDetails
) {

}
