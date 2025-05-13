package com.ssafy.tripon.like.application;

import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.common.exception.ErrorCode;
import com.ssafy.tripon.like.application.command.LikeSaveCommand;
import com.ssafy.tripon.like.domain.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;

    public void saveLike(LikeSaveCommand command) {
        likeRepository.save(command.toLike());
    }

    public Integer getCount(Integer reviewId) {
        return likeRepository.getCountByReviewId(reviewId);
    }

    public void deleteLike(Integer reviewId, String email) {
        int result = likeRepository.delete(reviewId, email);
        if(result == 0) {
        	throw new CustomException(ErrorCode.REVIEWS_NOT_FOUND);
        }
    }
}
