package com.ssafy.tripon.reviewdetail.presentation.request;

import java.util.List;

public record ReviewDetailSaveRequest(
        Integer day,
        String content,
        List<Integer> attractions
) {
}
