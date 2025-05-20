package com.ssafy.tripon.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.ssafy.tripon.common.auth.RefreshTokenService;
import com.ssafy.tripon.common.auth.TokenPair;
import com.ssafy.tripon.member.application.command.MemberLoginCommand;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() {
        String encodedPassword = passwordEncoder.encode("password123");
        memberRepository.save(new Member("test@test.com", "test_user", encodedPassword));
    }

    @Test
    void 로그인할_수_있다() {
        // given
        MemberLoginCommand command = new MemberLoginCommand("test@test.com", "password123");

        // when
        TokenPair tokens = memberService.login(command);

        // then
        assertThat(tokens.accessToken()).isNotNull();
        assertThat(tokens.refreshToken()).isNotNull();
    }
}
