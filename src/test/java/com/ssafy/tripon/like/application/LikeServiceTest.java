package com.ssafy.tripon.like.application;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.ssafy.tripon.like.application.command.LikeSaveCommand;
import com.ssafy.tripon.like.domain.LikeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRepository likeRepository;

    @Test
    void 좋아요를_저장할_수_있다() {
        // given
        LikeSaveCommand command = new LikeSaveCommand("admin@ssafy.com", 30);

        // when
        likeService.saveLike(command);

        // then
        Integer count = likeService.getCount(30);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void 좋아요를_삭제할_수_있다() {
        // given
        likeService.saveLike(new LikeSaveCommand("admin@ssafy.com", 31));
        assertThat(likeService.getCount(31)).isEqualTo(1);

        // when
        likeService.deleteLike(31, "admin@ssafy.com");

        // then
        assertThat(likeService.getCount(31)).isEqualTo(0);
    }

    @Test
    void 좋아요가_여러개_있을_때_정확히_카운트된다() {
        // given
        Integer reviewId = 30;

        likeService.saveLike(new LikeSaveCommand("admin@ssafy.com", reviewId));
        likeService.saveLike(new LikeSaveCommand("test@ssafy.com", reviewId));

        // when
        Integer count = likeService.getCount(reviewId);

        // then
        assertThat(count).isEqualTo(2);
    }
}