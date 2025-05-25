package com.ssafy.tripon.reviewdetail.application;

import com.ssafy.tripon.attraction.domain.Attraction;
import com.ssafy.tripon.attraction.domain.AttractionRepository;
import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.common.exception.ErrorCode;
import com.ssafy.tripon.common.utils.FileStorageService;
import com.ssafy.tripon.reviewattraction.domain.ReviewAttraction;
import com.ssafy.tripon.reviewattraction.domain.ReviewAttractionRepository;
import com.ssafy.tripon.reviewdetail.application.command.ReviewDetailSaveCommand;
import com.ssafy.tripon.reviewdetail.application.command.ReviewDetailUpdateCommand;
import com.ssafy.tripon.reviewdetail.domain.ReviewDetail;
import com.ssafy.tripon.reviewdetail.domain.ReviewDetailRepository;
import com.ssafy.tripon.reviewpicture.domain.ReviewPicture;
import com.ssafy.tripon.reviewpicture.domain.ReviewPictureRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		if (images != null) {
			for (MultipartFile image : images) {
				String storedUrl = fileStorageService.upload(image);
				pictures.add(storedUrl);
				reviewPictureRepository
						.save(new ReviewPicture(reviewDetail.getId(), image.getOriginalFilename(), storedUrl));
			}

		}
		return ReviewDetailServiceResponse.from(reviewDetail, attractions, pictures);
	}

	public ReviewDetailServiceResponse findReviewDetail(Integer id) {

		ReviewDetail reviewDetail = Optional.ofNullable(reviewDetailRepository.findById(id))
				.orElseThrow(() -> new CustomException(ErrorCode.REVIEWDETAILS_NOT_FOUND));

		// reviewdetails-attractions
		List<ReviewAttraction> reviewAttractions = reviewAttractionRepository.findAllByReviewDetailId(id);
		List<Attraction> attractions = new ArrayList<>();
		for (ReviewAttraction reviewAttraction : reviewAttractions) {
			attractions.add(attractionRepository.findAttractionById(reviewAttraction.getAttractionId()));
		}
		// reviewdetails-pictures
		List<ReviewPicture> reviewPictures = reviewPictureRepository.findAllByReviewDetailId(id);
		List<String> pictures = new ArrayList<>();
		for (ReviewPicture reviewPicture : reviewPictures) {
			pictures.add(reviewPicture.getUrl());
		}

		return ReviewDetailServiceResponse.from(reviewDetail, attractions, pictures);
	}

	public ReviewDetailServiceResponse updateReviewDetail(ReviewDetailUpdateCommand command,
			List<MultipartFile> images) {
		ReviewDetail reviewDetail = command.toReviewDetail();
		int result = reviewDetailRepository.update(reviewDetail);

		// 예외처리
		if (result == 0) {
			throw new CustomException(ErrorCode.REVIEWDETAILS_NOT_FOUND);
		}

		// S3에서 파일 삭제
		List<ReviewPicture> reviewPictures = reviewPictureRepository.findAllByReviewDetailId(reviewDetail.getId());
		for (ReviewPicture reviewPicture : reviewPictures) {
			fileStorageService.delete(reviewPicture.getUrl());
		}
		// 기존 데이터 삭제
		reviewAttractionRepository.deleteAllByReviewDetailId(reviewDetail.getId());
		reviewPictureRepository.deleteAllByReviewDetailId(reviewDetail.getId());

		// reviewdetails-attractions
		List<Attraction> attractions = new ArrayList<>();
		for (Integer attractionId : command.attractions()) {
			reviewAttractionRepository.save(new ReviewAttraction(reviewDetail.getId(), attractionId));
			attractions.add(attractionRepository.findAttractionById(attractionId));
		}

		// reviewdetails-pictures
		List<String> pictures = new ArrayList<>();
		if(images != null) {
			for (MultipartFile image : images) {
				String storedUrl = fileStorageService.upload(image);
				pictures.add(storedUrl);
				reviewPictureRepository
						.save(new ReviewPicture(reviewDetail.getId(), image.getOriginalFilename(), storedUrl));
			}	
		}
		

		return ReviewDetailServiceResponse.from(reviewDetail, attractions, pictures);
	}

	public void deleteReviewDetailById(Integer id) {
		// 해당 id 값의 reviewDetail이 있는지 검증
		Optional.ofNullable(reviewDetailRepository.findById(id))
				.orElseThrow(() -> new CustomException(ErrorCode.REVIEWDETAILS_NOT_FOUND));

		// S3에서 파일 삭제
		List<ReviewPicture> reviewPictures = reviewPictureRepository.findAllByReviewDetailId(id);
		for (ReviewPicture reviewPicture : reviewPictures) {
			fileStorageService.delete(reviewPicture.getUrl());
		}

		reviewAttractionRepository.deleteAllByReviewDetailId(id);
		reviewPictureRepository.deleteAllByReviewDetailId(id);
		reviewDetailRepository.deleteById(id);
	}

	public List<ReviewDetailServiceResponse> findReviewDetailByReviewId(Integer reviewId) {
		// reviewId로 연결된 모든 ReviewDetail 조회
		List<ReviewDetail> reviewDetails = reviewDetailRepository.findByReviewId(reviewId);

		if (reviewDetails.isEmpty()) {
			throw new CustomException(ErrorCode.REVIEWDETAILS_NOT_FOUND);
		}

		List<ReviewDetailServiceResponse> result = new ArrayList<>();

		for (ReviewDetail reviewDetail : reviewDetails) {
			Integer detailId = reviewDetail.getId();

			// 해당 reviewDetail에 연결된 attraction 조회
			List<ReviewAttraction> reviewAttractions = reviewAttractionRepository.findAllByReviewDetailId(detailId);
			List<Attraction> attractions = new ArrayList<>();
			for (ReviewAttraction reviewAttraction : reviewAttractions) {
				attractions.add(attractionRepository.findAttractionById(reviewAttraction.getAttractionId()));
			}

			// 해당 reviewDetail에 연결된 사진 조회
			List<ReviewPicture> reviewPictures = reviewPictureRepository.findAllByReviewDetailId(detailId);
			List<String> pictures = reviewPictures.stream().map(ReviewPicture::getUrl).toList();

			// 변환하여 리스트에 추가
			result.add(ReviewDetailServiceResponse.from(reviewDetail, attractions, pictures));
		}

		return result;
	}

}
