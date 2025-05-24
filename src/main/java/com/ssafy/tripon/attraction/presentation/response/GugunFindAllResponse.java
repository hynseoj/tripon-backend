package com.ssafy.tripon.attraction.presentation.response;

import com.ssafy.tripon.attraction.application.GugunResponse;
import java.util.List;

public record GugunFindAllResponse(
        List<GugunResponse> guguns
) {
    public static GugunFindAllResponse from(List<GugunResponse> guguns) {
        return new GugunFindAllResponse(guguns);
    }
}
