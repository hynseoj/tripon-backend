package com.ssafy.tripon.member.presentation;

import com.ssafy.tripon.common.auth.TokenPair;
import com.ssafy.tripon.member.application.MemberService;
import com.ssafy.tripon.member.presentation.request.LoginRequest;
import com.ssafy.tripon.member.presentation.response.LoginResponse;
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

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        TokenPair tokenPair = memberService.login(request.toCommand());
        return ResponseEntity.ok(LoginResponse.from(tokenPair));
    }


}
