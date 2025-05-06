package com.ssafy.tripon.review.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.ssafy.tripon.review.application.command.ReviewSaveCommand;
import com.ssafy.tripon.review.application.command.ReviewSaveCommand.ReviewDetailSaveCommand;
import com.ssafy.tripon.review.domain.ReviewAttractionRepository;
import com.ssafy.tripon.review.domain.ReviewDetailRepository;
import com.ssafy.tripon.review.domain.ReviewRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewDetailRepository reviewDetailRepository;

    @Autowired
    private ReviewAttractionRepository reviewAttractionRepository;


    @Test
    void 리뷰가_DB에_잘_저장되는지_확인() {
        // given
        ReviewSaveCommand command = new ReviewSaveCommand(
                "테스트 후기",
                List.of(
                        new ReviewDetailSaveCommand(
                                1,
                                "1일차 내용",
                                List.of(1001, 1002),
                                List.of()
                        ),
                        new ReviewDetailSaveCommand(
                                2,
                                "2일차 내용",
                                List.of(1001),
                                List.of()
                        )
                )
        );

        // when
        Integer reviewId = reviewService.saveReview(command);

        // then
        assertThat(reviewId).isNotNull();
    }
}