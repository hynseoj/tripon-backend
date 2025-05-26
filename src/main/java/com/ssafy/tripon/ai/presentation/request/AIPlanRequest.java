package com.ssafy.tripon.ai.presentation.request;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public record AIPlanRequest(
    String title,
    String location,
    LocalDate startDate,
    LocalDate endDate,
    String vibe,
    String memo
) {
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("location", location);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("vibe", vibe);
        map.put("memo", memo);
        return map;
    }
}
