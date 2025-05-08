package com.ssafy.tripon.reviewdetail.application;

import com.ssafy.tripon.reviewdetail.domain.ReviewDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewDetailService {

    private final ReviewDetailRepository reviewDetailRepository;

}
