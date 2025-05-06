package com.ssafy.tripon.notice.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    private Integer id;
    private String email;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Notice(String email, String title, String content) {
        this.email = email;
        this.title = title;
        this.content = content;
    }

    public Notice(Integer id, String email, String title, String content) {
        this.id = id;
        this.email = email;
        this.title = title;
        this.content = content;
    }
}
