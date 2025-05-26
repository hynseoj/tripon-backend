package com.ssafy.tripon.ai.presentation.request;

import java.util.HashMap;
import java.util.Map;

public record AIPlanUpdateRequest(
    String likeStyle,
    String dislikeStyle,
    String otherMemo
) {

	public Map<String, Object> toMap() {
	    return Map.of(
	        "like", this.likeStyle,
	        "dislike", this.dislikeStyle,
	        "etc", this.otherMemo
	    );
	}

}
