package com.ssafy.tripon.plan.presentation.request;

import com.ssafy.tripon.plan.application.command.PlanUpdateCommand;
import java.time.LocalDate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlanUpdateRequest(
		@NotBlank(message= "제목을 입력해주세요.")
		String title,
		@NotNull(message= "시작일을 설정해주세요.")
		@FutureOrPresent(message= "여행 시작일은 오늘 이후여야 합니다.")
		LocalDate startDate,
		@NotNull(message= "종료일을 설정해주세요.")
		@FutureOrPresent(message= "여행 종료일은 오늘 이후여야 합니다.")
		LocalDate endDate,
		String memo
		) {
	
	public PlanUpdateCommand toCommand(Integer planId) {
		return new PlanUpdateCommand(planId, title, startDate, endDate, memo);
	}
}
