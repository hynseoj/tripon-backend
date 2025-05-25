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
import com.ssafy.tripon.member.presentation.request.MemberUpdateRequest;
import com.ssafy.tripon.member.presentation.response.MemberResponse;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
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
		MemberRegisterCommand command = new MemberRegisterCommand("new1@test.com", "new_user", encodedPassword);

		// 리소스 경로에서 파일 불러오기
		ClassPathResource resource = new ClassPathResource("static/test.png");
		File file = resource.getFile();
		FileInputStream input = new FileInputStream(file);

		MultipartFile image = new MockMultipartFile("images", file.getName(), "image/png", input);

		// when
		LoginServiceResponse response = memberService.register(command, image);

		// then: DB에 회원이 저장된다
		Member savedMember = memberRepository.findByEmail("new1@test.com");

		assertThat(savedMember.getEmail()).isEqualTo("new1@test.com");
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

	@Test
	void 회원정보를_수정하고_토큰을_재발급받을_수_있다() {
		// given
		MemberUpdateRequest request = new MemberUpdateRequest("수정된이름", "새비밀번호123", "updated.png",
				"https://cdn.com/updated.png");

		// when
		LoginServiceResponse response = memberService.updateMember(request.toCommand(member.getEmail()));

		// then: DB에 반영되었는지 확인
		Member updated = memberRepository.findByEmail(member.getEmail());
		System.out.println(updated.getPassword());
		assertThat(updated.getName()).isEqualTo("수정된이름");
		assertThat(updated.getProfileImageName()).isEqualTo("updated.png");
		assertThat(updated.getProfileImageUrl()).isEqualTo("https://cdn.com/updated.png");
		assertThat(passwordEncoder.matches("새비밀번호123", updated.getPassword())).isTrue();

		// then: 토큰이 발급되었는지 확인
		assertThat(response.tokenPair().accessToken().token()).isNotBlank();
		assertThat(response.tokenPair().refreshToken().token()).isNotBlank();
		assertThat(response.name()).isEqualTo("수정된이름");
	}

	@Test
	void 비밀번호없이_회원정보를_수정하고_토큰을_재발급받을_수_있다() {
		// given
		String originalPassword = member.getPassword();

		MemberUpdateRequest request = new MemberUpdateRequest("비번없이수정", null, "updated2.png",
				"https://cdn.com/updated2.png");

		// when
		LoginServiceResponse response = memberService.updateMember(request.toCommand(member.getEmail()));

		// then: 이름과 프사만 바뀌고 비밀번호는 그대로
		Member updated = memberRepository.findByEmail(member.getEmail());
		assertThat(updated.getName()).isEqualTo("비번없이수정");
		assertThat(updated.getProfileImageName()).isEqualTo("updated2.png");
		assertThat(updated.getProfileImageUrl()).isEqualTo("https://cdn.com/updated2.png");
		assertThat(updated.getPassword()).isEqualTo(originalPassword);

		// then: 토큰도 정상 발급됨
		assertThat(response.tokenPair().accessToken().token()).isNotBlank();
		assertThat(response.tokenPair().refreshToken().token()).isNotBlank();
	}

	@Test
	void 회원정보를_조회할_수_있다() {
		// when
		MemberResponse response = memberService.findMemberByEmail(member.getEmail());

		// then
		assertThat(response.email()).isEqualTo(member.getEmail());
		assertThat(response.name()).isEqualTo(member.getName());
	}

}
