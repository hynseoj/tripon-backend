package com.ssafy.tripon.attraction.presentation.response;

import com.ssafy.tripon.attraction.application.ContentTypeResponse;
import java.util.List;

public record ContentTypeFindAllResponse(
        List<ContentTypeResponse> types
) {
    public static ContentTypeFindAllResponse from(List<ContentTypeResponse> types) {
        return new ContentTypeFindAllResponse(types);
    }
}
