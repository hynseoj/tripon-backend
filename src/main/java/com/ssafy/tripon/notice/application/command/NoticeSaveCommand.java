package com.ssafy.tripon.notice.application.command;

import com.ssafy.tripon.notice.domain.Notice;

public record NoticeSaveCommand(
        String email,
        String title,
        String content
) {
    public Notice toNotice() {
        return new Notice(email, title, content);
    }
}
