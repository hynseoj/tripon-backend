package com.ssafy.tripon.member.presentation.response;

import java.time.LocalDateTime;

import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.member.domain.Role;

public record MemberResponse(
		String email,
		String name,
		Role role,
		String profileImageName,
		String profileImageUrl,
		LocalDateTime createdAt
		) {

	public static MemberResponse from(Member member) {
		return new MemberResponse(member.getEmail(), member.getName(), member.getRole(), member.getProfileImageName(),
				member.getProfileImageUrl(), member.getCreatedAt());
	}

}