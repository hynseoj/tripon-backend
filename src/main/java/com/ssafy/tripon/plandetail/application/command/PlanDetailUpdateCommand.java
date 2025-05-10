package com.ssafy.tripon.plandetail.application.command;

import java.util.List;

import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;

public record PlanDetailUpdateCommand(
		Integer id,
		Integer day,
		List<AttractionSaveCommand> attractions
		) {
}
