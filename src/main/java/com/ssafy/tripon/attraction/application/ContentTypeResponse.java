package com.ssafy.tripon.attraction.application;

import com.ssafy.tripon.attraction.domain.ContentType;

public record ContentTypeResponse(
        Integer contentTypeId,
        String contentTypeName
) {
    public static ContentTypeResponse from(ContentType contentType) {
        return new ContentTypeResponse(contentType.getContentTypeId(), contentType.getContentTypeName());
    }
}
