package com.ssafy.tripon.member.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private String email;
    private String name;
    private String password;
    private Role role;
    private String profileImageName;
    private String profileImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = Role.USER;
    }

    public Member(String email, String name, String password, String profileImageName, String profileImageUrl) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = Role.USER;
        this.profileImageName = profileImageName;
        this.profileImageUrl = profileImageUrl;
    }
}
