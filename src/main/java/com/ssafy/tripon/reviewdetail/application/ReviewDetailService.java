package com.ssafy.tripon.reviewdetail.application;

import com.ssafy.tripon.attraction.domain.Attraction;
import com.ssafy.tripon.attraction.domain.AttractionRepository;
import com.ssafy.tripon.common.utils.FileStorageService;
import com.ssafy.tripon.reviewattraction.domain.ReviewAttraction;
import com.ssafy.tripon.reviewattraction.domain.ReviewAttractionRepository;
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
    private final ReviewAttractionRepository reviewAttractionRepository;
    private final ReviewPictureRepository reviewPictureRepository;
    private final AttractionRepository attractionRepository;

    private final FileStorageService fileStorageService;

    public ReviewDetailServiceResponse saveReviewDetail(ReviewDetailSaveCommand command, List<MultipartFile> images) {
        ReviewDetail reviewDetail = command.toReviewDetail();
        reviewDetailRepository.save(reviewDetail);

        // reviewdetails-attractions
        List<Attraction> attractions = new ArrayList<>();
        for (Integer attractionId : command.attractions()) {
            reviewAttractionRepository.save(new ReviewAttraction(reviewDetail.getId(), attractionId));
            attractions.add(attractionRepository.findAttractionById(attractionId));
        }

        // reviewdetails-pictures
        List<String> pictures = new ArrayList<>();
        for (MultipartFile image : images) {
            String storedUrl = fileStorageService.upload(image);
            pictures.add(storedUrl);
            reviewPictureRepository.save(new ReviewPicture(reviewDetail.getId(), image.getOriginalFilename(), storedUrl));
        }
        return ReviewDetailServiceResponse.from(reviewDetail, attractions, pictures);
    }

//    public ReviewDetailSaveResponse findReviewDetail(Integer id) {
//        ReviewDetail reviewDetail = reviewDetailRepository.findById(id);
//
//        // reviewdetails-attractions
//
//
//        // reviewdetails-pictures
//    }
}
