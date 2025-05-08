package com.ssafy.tripon.reviewdetail.application;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.ssafy.tripon.common.utils.FileStorageService;
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
    private ReviewPictureRepository reviewPictureRepository;

    @Autowired
    private FileStorageService s3Service;

    @Test
    void 일차별_리뷰글의_이미지_s3_업로드_테스트() throws Exception {
        // given
        ReviewDetailSaveCommand command = new ReviewDetailSaveCommand(
                2,
                1,
                "S3 테스트용 실제 이미지입니다",
                List.of(101, 102)
        );

        // 리소스 경로에서 파일 불러오기
        ClassPathResource resource = new ClassPathResource("static/test.png");
        File file = resource.getFile();
        FileInputStream input = new FileInputStream(file);

        MultipartFile image = new MockMultipartFile("images", file.getName(), "image/jpeg", input);

        // when
        ReviewDetailServiceResponse response = reviewDetailService.saveReviewDetail(command, List.of(image));

        // then
        assertThat(response).isNotNull();
        assertThat(response.pictures()).hasSize(1);

        // URL 확인
        String url = response.pictures().get(0);
        System.out.println(url);
        assertThat(url).isNotNull();
    }
}
