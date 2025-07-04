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
import com.ssafy.tripon.member.application.command.MemberUpdateCommand;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.member.domain.MemberRepository;
import com.ssafy.tripon.member.presentation.request.MemberUpdateRequest;
import com.ssafy.tripon.member.presentation.response.MemberResponse;
import com.ssafy.tripon.reviewpicture.domain.ReviewPicture;

import jakarta.validation.Valid;

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
		System.out.println("입력된 비밀번호: " + command.password());
		System.out.println("DB 비밀번호: " + foundMember.getPassword());
		System.out.println("matches: " + passwordEncoder.matches(command.password(), foundMember.getPassword()));

		if (!passwordEncoder.matches(command.password(), foundMember.getPassword())) {
			System.out.println(command.password());
			System.out.println(foundMember.getPassword());
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
		} else {
			member = command.toMember(encodedPassword);
		}
		memberRepository.save(member);

		Token accessToken = jwtTokenProvider.createAccessToken(member);
		Token refreshToken = jwtTokenProvider.createRefreshToken(member);

		refreshTokenService.saveRefreshToken(member.getEmail(), refreshToken);

		return LoginServiceResponse.of(member, new TokenPair(accessToken, refreshToken));
	}

	public LoginServiceResponse refreshToken(Token refreshToken) {
		jwtTokenProvider.validateToken(refreshToken);
		String memberEmail = jwtTokenProvider.getMemberEmail(refreshToken);
		Token storedRefreshToken = refreshTokenService.getRefreshToken(memberEmail);

		if (storedRefreshToken.token() == null || !storedRefreshToken.token().equals(refreshToken.token())) {
			throw new CustomException(UNAUTHORIZED);
		}

		Member foundMember = Optional.ofNullable(memberRepository.findByEmail(memberEmail))
				.orElseThrow(() -> new CustomException(USER_NOT_FOUND));

		Token newAccessToken = jwtTokenProvider.createAccessToken(foundMember);
		Token newRefreshToken = jwtTokenProvider.createRefreshToken(foundMember);

		refreshTokenService.saveRefreshToken(memberEmail, newRefreshToken);

		return LoginServiceResponse.of(foundMember, new TokenPair(newAccessToken, newRefreshToken));
	}

	public MemberResponse findMemberByEmail(String email) {
		return MemberResponse.from(memberRepository.findByEmail(email));
	}

	public LoginServiceResponse updateMember(MemberUpdateCommand command, MultipartFile profileImage) {
		// 프로필 사진 있으면 저장
		String storedUrl = null;
		if (profileImage != null) {
			storedUrl = fileStorageService.upload(profileImage);
		}
		if (command.password() != null && !command.password().isBlank()) {
			memberRepository.update(command.toMember(passwordEncoder.encode(command.password()),
					profileImage.getOriginalFilename(), storedUrl));
		} else {
			if (profileImage == null) {
				memberRepository.update(command.toMember(null, null));
			} else {
				memberRepository.update(command.toMember(profileImage.getOriginalFilename(), storedUrl));
			}

		}

		Member member = memberRepository.findByEmail(command.email());

		Token accessToken = jwtTokenProvider.createAccessToken(member);
		Token refreshToken = jwtTokenProvider.createRefreshToken(member);

		return new LoginServiceResponse(member.getName(), new TokenPair(accessToken, refreshToken),
				member.getProfileImageUrl());
	}

	public LoginServiceResponse deleteProfileImage(String email) {
		// 1. 사용자 조회
		Member member = memberRepository.findByEmail(email);
		System.out.println(email);
		
		// s3에서 이미지 삭제
		fileStorageService.delete(member.getProfileImageUrl());

		// 2. 이미지 정보 초기화
		member.setProfileImageName(null);
		member.setProfileImageUrl(null);
		memberRepository.update(member);

		// 3. 새로운 토큰 발급
		Token accessToken = jwtTokenProvider.createAccessToken(member);
		Token refreshToken = jwtTokenProvider.createRefreshToken(member);

		return new LoginServiceResponse(member.getName(), new TokenPair(accessToken, refreshToken),
				member.getProfileImageUrl());
	}

}
