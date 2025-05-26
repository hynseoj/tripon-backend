package com.ssafy.tripon.ai.presentation.response;

import java.time.LocalDate;
import java.util.List;

public record AIPlanResponse(
    String title,
    LocalDate startDate,
    LocalDate endDate,
    Integer day,
    List<AttractionResponse> attractions
) {}
