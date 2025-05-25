package com.ssafy.tripon.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ssafy.tripon.common.auth.JwtTokenProvider;
import com.ssafy.tripon.common.auth.RefreshTokenService;
import com.ssafy.tripon.common.auth.TokenBlacklistService;
import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.member.application.command.MemberLoginCommand;
import com.ssafy.tripon.member.application.command.MemberRegisterCommand;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.member.domain.MemberRepository;
import java.io.File;
import java.io.FileInputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    private Member member;

    @BeforeEach
    void setUp() {
        String encodedPassword = passwordEncoder.encode("password123");
        member = new Member("test@test.com", "test_user", encodedPassword);
        memberRepository.save(member);
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteByEmail(member.getEmail());
        refreshTokenService.deleteRefreshToken(member.getEmail());
    }

    @Test
    void 로그인할_수_있다() {
        // given
        MemberLoginCommand command = new MemberLoginCommand("test@test.com", "password123");

        // when
        LoginServiceResponse response = memberService.login(command);

        // then
        assertThat(response.tokenPair().accessToken()).isNotNull();
        assertThat(response.tokenPair().refreshToken()).isNotNull();
    }

    @Test
    void 로그아웃할_수_있다() {
        // given
        MemberLoginCommand command = new MemberLoginCommand("test@test.com", "password123");
        LoginServiceResponse response = memberService.login(command);

        // when
        memberService.logout(member, response.tokenPair().accessToken());

        // then
        String jti = jwtTokenProvider.getJti(response.tokenPair().accessToken());
        assertThat(tokenBlacklistService.isBlacklisted(jti)).isTrue();
        assertThat(refreshTokenService.getRefreshToken(member.getEmail()).token()).isNull();
    }

    @Test
    void 회원가입할_수_있다() throws Exception {
        // given
        String encodedPassword = passwordEncoder.encode("password123");
        MemberRegisterCommand command = new MemberRegisterCommand("new@test.com", "new_user", encodedPassword);

        // 리소스 경로에서 파일 불러오기
        ClassPathResource resource = new ClassPathResource("static/test.png");
        File file = resource.getFile();
        FileInputStream input = new FileInputStream(file);

        MultipartFile image = new MockMultipartFile("images", file.getName(), "image/png", input);

        // when
        LoginServiceResponse response = memberService.register(command, image);

        // then: DB에 회원이 저장된다
        Member savedMember = memberRepository.findByEmail("new@test.com");

        assertThat(savedMember.getEmail()).isEqualTo("new@test.com");
        assertThat(savedMember.getName()).isEqualTo("new_user");
        assertThat(savedMember.getProfileImageName()).isEqualTo("test.png");
        assertThat(savedMember.getProfileImageUrl()).isNotBlank();

        // then: 토큰 페어가 발급된다
        assertThat(response.tokenPair().accessToken().token()).isNotNull();
        assertThat(response.tokenPair().refreshToken().token()).isNotNull();
    }

    @Test
    void 존재하는_이메일로_회원가입을_시도하면_예외가_발생한다() throws Exception {
        // given
        String encodedPassword = passwordEncoder.encode("password123");
        MemberRegisterCommand command = new MemberRegisterCommand("test@test.com", "new_user", encodedPassword);

        // 리소스 경로에서 파일 불러오기
        ClassPathResource resource = new ClassPathResource("static/test.png");
        File file = resource.getFile();
        FileInputStream input = new FileInputStream(file);

        MultipartFile image = new MockMultipartFile("images", file.getName(), "image/png", input);

        // when & then
        assertThrows(CustomException.class, () -> memberService.register(command, image));
    }
}
