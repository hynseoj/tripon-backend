package com.ssafy.tripon.review.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.ssafy.tripon.review.application.command.ReviewSaveCommand;
import com.ssafy.tripon.review.application.command.ReviewUpdateCommand;
import com.ssafy.tripon.review.domain.Review;
import com.ssafy.tripon.review.domain.ReviewRepository;
import com.ssafy.tripon.reviewdetail.domain.ReviewDetail;
import com.ssafy.tripon.reviewdetail.domain.ReviewDetailRepository;
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

    @Test
    void 리뷰를_저장할_수_있다() {
        // given
        ReviewSaveCommand command = new ReviewSaveCommand("admin@ssafy.com", "첫 리뷰입니다.");

        // when
        Integer id = reviewService.saveReview(command);

        // then
        Review review = reviewRepository.findById(id);
        assertThat(review.getTitle()).isEqualTo("첫 리뷰입니다.");
        assertThat(review.getMemberEmail()).isEqualTo("admin@ssafy.com");
    }

    @Test
    void 리뷰_목록을_조회할_수_있다() {
        // given
        reviewService.saveReview(new ReviewSaveCommand("admin@ssafy.com", "첫 리뷰입니다."));
        reviewService.saveReview(new ReviewSaveCommand("admin@ssafy.com", "두 번째 리뷰입니다."));

        // when
        List<ReviewServiceResponse> reviews = reviewService.findAllReviews("admin@ssafy.com");

        // then
        assertThat(reviews).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void 리뷰를_아이디로_조회할_수_있다() {
        // given
        int savedId = reviewService.saveReview(new ReviewSaveCommand("admin@ssafy.com", "첫 리뷰입니다."));
        reviewDetailRepository.save(new ReviewDetail(savedId, 1, "첫 째날"));
        reviewDetailRepository.save(new ReviewDetail(savedId, 2, "둘 째날"));

        // when
        ReviewServiceResponse response = reviewService.findReview(savedId, "admin@ssafy.com");

        // then
        assertThat(response.title()).isEqualTo("첫 리뷰입니다.");
        assertThat(response.details()).isNotNull();
        assertThat(response.details()).hasSize(2);
    }

    @Test
    void 리뷰를_수정할_수_있다() {
        // given
        int savedId = reviewService.saveReview(new ReviewSaveCommand("admin@ssafy.com", "첫 리뷰입니다."));
        ReviewUpdateCommand updateCommand = new ReviewUpdateCommand(savedId, "admin@ssafy.com", "수정된 제목입니다.");

        // when
        ReviewServiceResponse response = reviewService.updateReview(updateCommand);

        // then
        assertThat(response.title()).isEqualTo("수정된 제목입니다.");
    }

    @Test
    void 리뷰를_삭제할_수_있다() {
        // given
        int savedId = reviewService.saveReview(new ReviewSaveCommand("admin@ssafy.com", "첫 리뷰입니다."));

        // when
        reviewService.deleteReview(savedId);

        // then
        List<ReviewServiceResponse> reviews = reviewService.findAllReviews("admin@ssafy.com");
        assertThat(reviews.stream().anyMatch(r -> r.id().equals(savedId))).isFalse();
    }
}
