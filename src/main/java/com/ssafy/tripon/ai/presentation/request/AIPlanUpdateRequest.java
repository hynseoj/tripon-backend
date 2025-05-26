package com.ssafy.tripon.ai.presentation.request;

import java.util.HashMap;
import java.util.Map;

public record AIPlanUpdateRequest(
    String previousPlan,
    String userPrompt
) {

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("previousPlan", previousPlan);
        map.put("userPrompt", userPrompt);
        return map;
    }
}
