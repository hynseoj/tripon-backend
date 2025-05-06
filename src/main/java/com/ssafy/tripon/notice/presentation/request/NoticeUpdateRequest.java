package com.ssafy.tripon.notice.presentation.request;

import com.ssafy.tripon.notice.application.command.NoticeUpdateCommand;

public record NoticeUpdateRequest(
        String title,
        String content
) {
    public NoticeUpdateCommand toCommand(Integer id, String email) {
        return new NoticeUpdateCommand(id, email, title, content);
    }
}
