package com.ssafy.tripon.attraction.application;

import com.ssafy.tripon.attraction.domain.Gugun;

public record GugunResponse(
        Integer gugunCode,
        String gugunName
) {
    public static GugunResponse from(Gugun gugun) {
        return new GugunResponse(gugun.getGugunCode(), gugun.getGugunName());
    }
}
