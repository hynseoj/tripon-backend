package com.ssafy.tripon.member.application;

import static com.ssafy.tripon.common.exception.ErrorCode.UNAUTHORIZED;
import static com.ssafy.tripon.common.exception.ErrorCode.USER_NOT_FOUND;

import com.ssafy.tripon.common.auth.JwtTokenProvider;
import com.ssafy.tripon.common.auth.RefreshTokenService;
import com.ssafy.tripon.common.auth.Token;
import com.ssafy.tripon.common.auth.TokenBlacklistService;
import com.ssafy.tripon.common.auth.TokenPair;
import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.member.application.command.MemberLoginCommand;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.member.domain.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenBlacklistService blacklistService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenPair login(MemberLoginCommand command) {
        Member foundMember = Optional.ofNullable(memberRepository.findByEmail(command.email()))
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(command.password(), foundMember.getPassword())) {
            throw new CustomException(UNAUTHORIZED);
        }

        Token accessToken = jwtTokenProvider.createAccessToken(foundMember);
        Token refreshToken = jwtTokenProvider.createRefreshToken(foundMember);

        refreshTokenService.saveRefreshToken(foundMember.getEmail(), refreshToken);

        return new TokenPair(accessToken, refreshToken);
    }
}
