package com.ssafy.tripon.review.application.command;

import com.ssafy.tripon.review.domain.Review;
import com.ssafy.tripon.review.domain.ReviewDetail;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ReviewSaveCommand(
        String title,
        List<ReviewDetailSaveCommand> details
) {
    public record ReviewDetailSaveCommand(
            Integer day,
            String content,
            List<Integer> attractions,
            List<MultipartFile> pictures
    ) {
        public ReviewDetail toReviewDetail(Integer reviewId) {
            return new ReviewDetail(reviewId, day, content);
        }
    }

    public Review toReview() {
        return new Review("admin@ssafy.com", title); // @Todo: 나중에 로그인 구현하고, 사용자 정보 넣는 걸로 바꾸기
    }
}
