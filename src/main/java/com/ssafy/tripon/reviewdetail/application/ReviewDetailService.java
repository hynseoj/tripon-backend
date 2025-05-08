package com.ssafy.tripon.reviewdetail.application;

import com.ssafy.tripon.common.utils.FileStorageService;
import com.ssafy.tripon.reviewdetail.application.command.ReviewDetailSaveCommand;
import com.ssafy.tripon.reviewdetail.domain.ReviewDetail;
import com.ssafy.tripon.reviewdetail.domain.ReviewDetailRepository;
import com.ssafy.tripon.reviewpicture.domain.ReviewPicture;
import com.ssafy.tripon.reviewpicture.domain.ReviewPictureRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewDetailService {

    private final ReviewDetailRepository reviewDetailRepository;
    private final ReviewPictureRepository reviewPictureRepository;

    private final FileStorageService fileStorageService;

    public ReviewDetailServiceResponse saveReviewDetail(ReviewDetailSaveCommand command, List<MultipartFile> images) {
        ReviewDetail reviewDetail = command.toReviewDetail();
        reviewDetailRepository.save(reviewDetail);

        List<String> pictures = new ArrayList<>();
        for (MultipartFile image : images) {
            String storedUrl = fileStorageService.upload(image);
            pictures.add(storedUrl);
            reviewPictureRepository.save(new ReviewPicture(reviewDetail.getId(), image.getOriginalFilename(), storedUrl));
        }
        return ReviewDetailServiceResponse.from(reviewDetail, command.attractions(), pictures);
    }
}
