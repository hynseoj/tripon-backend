package com.ssafy.tripon.reviewdetail.application;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.ssafy.tripon.common.utils.FileStorageService;
import com.ssafy.tripon.reviewattraction.domain.ReviewAttractionRepository;
import com.ssafy.tripon.reviewdetail.application.command.ReviewDetailSaveCommand;
import com.ssafy.tripon.reviewdetail.application.command.ReviewDetailUpdateCommand;
import com.ssafy.tripon.reviewdetail.domain.ReviewDetailRepository;
import com.ssafy.tripon.reviewpicture.domain.ReviewPictureRepository;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@Transactional
class ReviewDetailServiceTest {

    @Autowired
    private ReviewDetailService reviewDetailService;

    @Autowired
    private ReviewDetailRepository reviewDetailRepository;

    @Autowired
    private ReviewAttractionRepository reviewAttractionRepository;

    @Autowired
    private ReviewPictureRepository reviewPictureRepository;

    @Autowired
    private FileStorageService s3Service;

    @Test
    void 일차별_리뷰를_저장할_수_있다() throws Exception {
        // given
        List<Integer> attractionIds = List.of(1001, 1002);
        ReviewDetailSaveCommand command = new ReviewDetailSaveCommand(
                31,
                1,
                "S3 테스트용 실제 이미지입니다",
                attractionIds
        );

        // 리소스 경로에서 파일 불러오기
        ClassPathResource resource = new ClassPathResource("static/test.png");
        File file = resource.getFile();
        FileInputStream input = new FileInputStream(file);

        MultipartFile image = new MockMultipartFile("images", file.getName(), "image/png", input);

        // when
        ReviewDetailServiceResponse response = reviewDetailService.saveReviewDetail(command, List.of(image));

        // then
        assertThat(response).isNotNull();
        assertThat(response.pictures()).hasSize(1);
        assertThat(response.attractions()).hasSize(2);

        // 실제로 저장된 리뷰, 사진, 관광지 연관 데이터 확인
        assertThat(reviewDetailRepository.findById(response.id())).isNotNull();
        assertThat(reviewPictureRepository.findAllByReviewDetailId(response.id())).hasSize(1);
        assertThat(reviewAttractionRepository.findAllByReviewDetailId(response.id())).hasSize(attractionIds.size());

        // 출력: S3 URL 확인
        System.out.println("S3 URL: " + response.pictures().get(0));
    }

    @Test
    void 일차별_리뷰를_아이디로_조회할_수_있다() throws Exception {
        // given
        List<Integer> attractionIds = List.of(1001, 1002);
        ReviewDetailSaveCommand command = new ReviewDetailSaveCommand(31, 1, "조회 테스트", attractionIds);

        ClassPathResource resource = new ClassPathResource("static/test.png");
        MultipartFile image = new MockMultipartFile("images", resource.getFilename(), "image/png", new FileInputStream(resource.getFile()));

        ReviewDetailServiceResponse saved = reviewDetailService.saveReviewDetail(command, List.of(image));

        // when
        ReviewDetailServiceResponse found = reviewDetailService.findReviewDetail(saved.id());

        // then
        assertThat(found.content()).isEqualTo("조회 테스트");
        assertThat(found.pictures()).hasSize(1);
        assertThat(found.attractions()).hasSize(2);
    }

    @Test
    void 일차별_리뷰를_수정할_수_있다() throws Exception {
        // given
        ReviewDetailSaveCommand saveCommand = new ReviewDetailSaveCommand(31, 1, "수정 전", List.of(1001));
        MultipartFile image = new MockMultipartFile("images", "test.png", "image/png", new FileInputStream(new ClassPathResource("static/test.png").getFile()));
        ReviewDetailServiceResponse saved = reviewDetailService.saveReviewDetail(saveCommand, List.of(image));

        // when
        List<Integer> newAttractionIds = List.of(1001, 1002);
        ReviewDetailUpdateCommand updateCommand = new ReviewDetailUpdateCommand(saved.id(), 31, 2, "수정 후", newAttractionIds);
        MultipartFile newImage = new MockMultipartFile("images", "test.png", "image/png", new FileInputStream(new ClassPathResource("static/test.png").getFile()));
        ReviewDetailServiceResponse updated = reviewDetailService.updateReviewDetail(updateCommand, List.of(newImage));

        // then
        assertThat(updated.content()).isEqualTo("수정 후");
        assertThat(updated.attractions()).hasSize(2);
        assertThat(updated.pictures()).hasSize(1);

        // 출력: S3 URL 확인
        System.out.println("Original S3 URL: " + saved.pictures().get(0));
        System.out.println("Updated S3 URL: " + updated.pictures().get(0));
    }

    @Test
    void 일차별_리뷰를_삭제할_수_있다() throws Exception {
        // given
        ReviewDetailSaveCommand command = new ReviewDetailSaveCommand(31, 1, "삭제 테스트", List.of(1001));
        MultipartFile image = new MockMultipartFile("images", "test.png", "image/png", new FileInputStream(new ClassPathResource("static/test.png").getFile()));
        ReviewDetailServiceResponse saved = reviewDetailService.saveReviewDetail(command, List.of(image));

        // when
        reviewDetailService.deleteReviewDetailById(saved.id());

        // then
        assertThat(reviewDetailRepository.findById(saved.id())).isNull();
        assertThat(reviewAttractionRepository.findAllByReviewDetailId(saved.id())).isEmpty();
        assertThat(reviewPictureRepository.findAllByReviewDetailId(saved.id())).isEmpty();
    }

}
