package com.ssafy.tripon.notice.presentation.response;

import com.ssafy.tripon.notice.application.NoticeServiceResponse;
import java.time.LocalDateTime;

public record NoticeSaveResponse(
        Integer id,
        String email,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static NoticeSaveResponse from(NoticeServiceResponse response) {
        return new NoticeSaveResponse(
                response.id(),
                response.email(),
                response.title(),
                response.content(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}

