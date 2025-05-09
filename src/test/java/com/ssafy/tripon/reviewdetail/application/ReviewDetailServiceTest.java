package com.ssafy.tripon.reviewdetail.application;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.ssafy.tripon.common.utils.FileStorageService;
import com.ssafy.tripon.reviewattraction.domain.ReviewAttractionRepository;
import com.ssafy.tripon.reviewdetail.application.command.ReviewDetailSaveCommand;
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
        assertThat(response.attractions()).containsExactlyInAnyOrderElementsOf(attractionIds);

        // 실제로 저장된 리뷰, 사진, 관광지 연관 데이터 확인
        assertThat(reviewDetailRepository.findById(response.id())).isNotNull();
        assertThat(reviewPictureRepository.findAllByReviewDetailId(response.id())).hasSize(1);
        assertThat(reviewAttractionRepository.findAllByReviewDetailId(response.id())).hasSize(attractionIds.size());

        // 출력: S3 URL 확인
        System.out.println("S3 URL: " + response.pictures().get(0));
    }
}
