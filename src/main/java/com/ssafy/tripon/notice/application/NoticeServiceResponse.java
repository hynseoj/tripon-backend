package com.ssafy.tripon.notice.application;

import com.ssafy.tripon.notice.domain.Notice;
import java.time.LocalDateTime;

public record NoticeServiceResponse(
        Integer id,
        String email,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static NoticeServiceResponse from(Notice notice) {
        return new NoticeServiceResponse(
                notice.getId(),
                notice.getEmail(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCreatedAt(),
                notice.getUpdatedAt()
        );
    }
}
