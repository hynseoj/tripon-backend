package com.ssafy.tripon.notice.presentation.request;

import com.ssafy.tripon.notice.application.command.NoticeSaveCommand;

public record NoticeSaveRequest(
        String title,
        String content
) {
    public NoticeSaveCommand toCommand(String email) {
        return new NoticeSaveCommand(email, title, content);
    }
}
