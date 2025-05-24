package com.ssafy.tripon.attraction.application;

import java.util.List;

public record AttractionCursorPage(
        List<AttractionServiceResponse> content,
        Integer nextCursor
) {
    public static AttractionCursorPage of(List<AttractionServiceResponse> content, Integer nextCursor) {
        return new AttractionCursorPage(content, nextCursor);
    }
}
