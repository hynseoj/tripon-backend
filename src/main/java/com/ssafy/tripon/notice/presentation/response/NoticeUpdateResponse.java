package com.ssafy.tripon.notice.presentation.response;

import com.ssafy.tripon.notice.application.NoticeServiceResponse;
import java.time.LocalDateTime;

public record NoticeUpdateResponse(
        Integer id,
        String email,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static NoticeUpdateResponse from(NoticeServiceResponse response) {
        return new NoticeUpdateResponse(
                response.id(),
                response.email(),
                response.title(),
                response.content(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
