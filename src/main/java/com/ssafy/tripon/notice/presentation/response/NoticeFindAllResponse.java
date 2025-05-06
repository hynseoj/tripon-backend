package com.ssafy.tripon.notice.presentation.response;

import com.ssafy.tripon.notice.application.NoticeServiceResponse;
import java.util.List;

public record NoticeFindAllResponse(
    List<NoticeFindResponse> notices
) {
    public static NoticeFindAllResponse from(List<NoticeServiceResponse> responses) {
        return new NoticeFindAllResponse(
                responses.stream()
                        .map(NoticeFindResponse::from)
                        .toList()
        );
    }
}
