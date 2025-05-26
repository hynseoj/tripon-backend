package com.ssafy.tripon.ai.presentation.request;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public record AIPlanRequest(
    LocalDate startDate,
    LocalDate endDate,
    String likeStyle,
    String dislikeStyle,
    String otherMemo
) {
	public Map<String, Object> toMap() {
	    return Map.of(
	        "startDate", this.startDate.toString(),
	        "endDate", this.endDate.toString(),
	        "like", this.likeStyle,
	        "dislike", this.dislikeStyle,
	        "etc", this.otherMemo
	    );
	}

}
