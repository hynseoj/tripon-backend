package com.ssafy.tripon.ai.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.tripon.ai.application.AIChatService;
import com.ssafy.tripon.ai.presentation.request.AIPlanRequest;
import com.ssafy.tripon.ai.presentation.request.AIPlanUpdateRequest;
import com.ssafy.tripon.ai.presentation.response.AIPlanResponse;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AIController {

	private final AIChatService aiChatService;

	/**
	 * 여행 계획 최초 추천
	 */
	@PostMapping("/planCreate")
	public ResponseEntity<AIPlanResponse> createPlan(@RequestBody AIPlanRequest request) {
		AIPlanResponse plan = aiChatService.createPlan(request);
		return ResponseEntity.ok(plan);
	}

	/**
	 * AI에게 추가 프롬프트 전달하여 계획 수정
	 */
	@PostMapping("/planUpdate")
	public ResponseEntity<AIPlanResponse> updatePlan (
			@RequestBody AIPlanUpdateRequest request
	) {

		AIPlanResponse updated = aiChatService.updatePlan(request);
		System.out.println(updated);
		return ResponseEntity.ok(updated);
	}
}
