package com.ssafy.tripon.member.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.ssafy.tripon.common.auth.Token;
import com.ssafy.tripon.common.auth.config.AuthToken;
import com.ssafy.tripon.common.auth.config.LoginMember;
import com.ssafy.tripon.member.application.LoginServiceResponse;
import com.ssafy.tripon.member.application.MemberService;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.member.presentation.request.LoginRequest;
import com.ssafy.tripon.member.presentation.request.MemberRegisterRequest;
import com.ssafy.tripon.member.presentation.response.LoginResponse;
import jakarta.validation.Valid;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginServiceResponse response = memberService.login(request.toCommand());
        return ResponseEntity.status(CREATED).body(LoginResponse.from(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@LoginMember Member member, @AuthToken Token token) {
        memberService.logout(member, token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @Valid @RequestPart(value = "memberInfo") MemberRegisterRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) {
        LoginServiceResponse response = memberService.register(request.toCommand(), profileImage);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", response.tokenPair().refreshToken().token())
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(LoginResponse.from(response));
    }
}
