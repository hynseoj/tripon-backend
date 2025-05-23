package com.ssafy.tripon.member.application;

import static com.ssafy.tripon.common.exception.ErrorCode.DUPLICATE_RESOURCE;
import static com.ssafy.tripon.common.exception.ErrorCode.UNAUTHORIZED;
import static com.ssafy.tripon.common.exception.ErrorCode.USER_NOT_FOUND;

import com.ssafy.tripon.common.auth.JwtTokenProvider;
import com.ssafy.tripon.common.auth.RefreshTokenService;
import com.ssafy.tripon.common.auth.Token;
import com.ssafy.tripon.common.auth.TokenBlacklistService;
import com.ssafy.tripon.common.auth.TokenPair;
import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.common.utils.FileStorageService;
import com.ssafy.tripon.member.application.command.MemberLoginCommand;
import com.ssafy.tripon.member.application.command.MemberRegisterCommand;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.member.domain.MemberRepository;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenBlacklistService blacklistService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    private final FileStorageService fileStorageService;

    public LoginServiceResponse login(MemberLoginCommand command) {
        Member foundMember = Optional.ofNullable(memberRepository.findByEmail(command.email()))
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(command.password(), foundMember.getPassword())) {
            throw new CustomException(UNAUTHORIZED);
        }

        Token accessToken = jwtTokenProvider.createAccessToken(foundMember);
        Token refreshToken = jwtTokenProvider.createRefreshToken(foundMember);

        refreshTokenService.saveRefreshToken(foundMember.getEmail(), refreshToken);

        return LoginServiceResponse.of(foundMember, new TokenPair(accessToken, refreshToken));
    }

    public void logout(Member member, Token token) {
        String jti = jwtTokenProvider.getJti(token);
        Date expiration = jwtTokenProvider.getExpiration(token);
        long leftTime = expiration.getTime() - System.currentTimeMillis();

        if (leftTime > 0) {
            blacklistService.blacklistToken(jti, Duration.ofMillis(leftTime));
        }

        refreshTokenService.deleteRefreshToken(member.getEmail());
    }

    public LoginServiceResponse register(MemberRegisterCommand command, MultipartFile image) {
        if (memberRepository.existsByEmail(command.email())) {
            throw new CustomException(DUPLICATE_RESOURCE);
        }

        Member member;
        String encodedPassword = passwordEncoder.encode(command.password());
        if (image != null && !image.isEmpty()) {
            String storedUrl = fileStorageService.upload(image);
            member = command.toMember(encodedPassword, image.getOriginalFilename(), storedUrl);
        }
        else {
            member = command.toMember(encodedPassword);
        }
        memberRepository.save(member);

        Token accessToken = jwtTokenProvider.createAccessToken(member);
        Token refreshToken = jwtTokenProvider.createRefreshToken(member);

        refreshTokenService.saveRefreshToken(member.getEmail(), refreshToken);

        return LoginServiceResponse.of(member, new TokenPair(accessToken, refreshToken));
    }
}
