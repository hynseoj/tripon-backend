package com.ssafy.tripon.member.presentation;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.tripon.common.auth.JwtTokenProvider;
import com.ssafy.tripon.common.auth.Token;
import com.ssafy.tripon.common.auth.config.AuthToken;
import com.ssafy.tripon.common.auth.config.LoginMember;
import com.ssafy.tripon.member.application.LoginServiceResponse;
import com.ssafy.tripon.member.application.MemberService;
import com.ssafy.tripon.member.application.command.MemberUpdateCommand;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.member.presentation.request.LoginRequest;
import com.ssafy.tripon.member.presentation.request.MemberRegisterRequest;
import com.ssafy.tripon.member.presentation.request.MemberUpdateRequest;
import com.ssafy.tripon.member.presentation.response.LoginResponse;
import com.ssafy.tripon.member.presentation.response.MemberResponse;
import com.ssafy.tripon.member.presentation.response.MemberUpdateResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		LoginServiceResponse response = memberService.login(request.toCommand());
		System.out.println(response);
		ResponseCookie cookie = ResponseCookie.from("refreshToken", response.tokenPair().refreshToken().token())
				.httpOnly(true).path("/").maxAge(Duration.ofDays(7)).build();

		System.out.println("로그인 끝!!!");

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(LoginResponse.from(response));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@LoginMember Member member, @AuthToken Token token) {
		memberService.logout(member, token);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/register")
	public ResponseEntity<LoginResponse> register(
			@Valid @RequestPart(value = "memberInfo") MemberRegisterRequest request,
			@RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
		LoginServiceResponse response = memberService.register(request.toCommand(), profileImage);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", response.tokenPair().refreshToken().token())
				.httpOnly(true).path("/").maxAge(Duration.ofDays(7)).build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(LoginResponse.from(response));
	}

	@PostMapping("/token/refresh")
	public ResponseEntity<LoginResponse> refresh(@CookieValue(name = "refreshToken") String refreshToken) {
		LoginServiceResponse response = memberService.refreshToken(new Token(refreshToken));

		ResponseCookie cookie = ResponseCookie.from("refreshToken", response.tokenPair().refreshToken().token())
				.httpOnly(true).path("/").maxAge(Duration.ofDays(7)).build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(LoginResponse.from(response));
	}

	// 로그인한 사용자 정보 조회
	@GetMapping("/me")
	public ResponseEntity<MemberResponse> findMyInfo(@LoginMember Member member) {
		MemberResponse response = memberService.findMemberByEmail(member.getEmail());
		return ResponseEntity.ok(response);
	}

	// 사용자 정보 수정
	@PutMapping("/me")
	public ResponseEntity<MemberUpdateResponse> updateMyInfo(@LoginMember Member member,
			@RequestPart(value = "memberInfo") @Valid MemberUpdateRequest request,
			@RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
		LoginServiceResponse response = memberService.updateMember(request.toCommand(member.getEmail()), profileImage);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", response.tokenPair().refreshToken().token())
				.httpOnly(true).path("/").maxAge(Duration.ofDays(7)).build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(MemberUpdateResponse.from(response));
	}

	@DeleteMapping("/me/profile")
	public ResponseEntity<MemberUpdateResponse> deleteProfileImage(@LoginMember Member member) {
		LoginServiceResponse response = memberService.deleteProfileImage(member.getEmail());

		ResponseCookie cookie = ResponseCookie.from("refreshToken", response.tokenPair().refreshToken().token())
				.httpOnly(true).path("/").maxAge(Duration.ofDays(7)).build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(MemberUpdateResponse.from(response));
	}

}
