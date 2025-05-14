package com.ssafy.tripon.member.presentation;

import com.ssafy.tripon.common.auth.JwtToken;
import com.ssafy.tripon.common.auth.JwtTokenProvider;
import com.ssafy.tripon.common.auth.RefreshTokenService;
import com.ssafy.tripon.common.auth.TokenBlacklistService;
import com.ssafy.tripon.member.application.MemberService;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.member.presentation.request.LoginRequest;
import com.ssafy.tripon.member.presentation.response.LoginResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final TokenBlacklistService blacklistService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Member member = memberService.authenticate(request.toCommand());

        JwtToken accessToken = jwtTokenProvider.createAccessToken(member);
        JwtToken refreshToken = jwtTokenProvider.createRefreshToken(member);

        long ttlMillis = jwtTokenProvider.getRefreshExpirationMillis();
        refreshTokenService.saveRefreshToken(member.getEmail(), refreshToken, Duration.ofMillis(ttlMillis));

        return ResponseEntity.ok(new LoginResponse(accessToken.token(), refreshToken.token()));
    }
}
