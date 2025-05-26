package com.ssafy.tripon.plandetail.presentation.request;

import com.ssafy.tripon.plandetail.application.command.PlanDetailUpdateCommand;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PlanDetailUpdateRequest(
		@NotNull
		@Positive
		Integer day,
		@Valid
		@NotEmpty(message = "관광지를 1개 이상 선택해주세요.")
		List<Integer> attractions
) {
	public PlanDetailUpdateCommand toCommand(int id) {
		return new PlanDetailUpdateCommand(id, day, attractions);
	}
}
