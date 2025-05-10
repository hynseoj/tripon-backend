package com.ssafy.tripon.plan.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan {
	private Integer id;
	private String email;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private String memo;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public Plan(String email, String title, LocalDate startDtate, LocalDate endDate, String memo) {
		this.email = email;
		this.title = title;
		this.startDate = startDtate;
		this.endDate = endDate;
		this.memo = memo;
	}
	
	public Plan(Integer id, String title, LocalDate startDtate, LocalDate endDate, String memo) {
		this.id = id;
		this.title = title;
		this.startDate = startDtate;
		this.endDate = endDate;
		this.memo = memo;
	}
}
