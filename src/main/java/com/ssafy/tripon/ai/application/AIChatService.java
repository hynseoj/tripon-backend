package com.ssafy.tripon.ai.application;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.tripon.ai.presentation.request.AIPlanRequest;
import com.ssafy.tripon.ai.presentation.request.AIPlanUpdateRequest;
import com.ssafy.tripon.ai.presentation.response.AIPlanResponse;
import com.ssafy.tripon.attraction.domain.Attraction;
import com.ssafy.tripon.attraction.application.AttractionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AIChatService {

	private final ObjectMapper objectMapper;
	private final AttractionService attractionService;
	private final ChatClient advisedChatClient; // 생성 & 수정 둘 다 이걸 씀

	// 최초 여행 계획 생성
	public AIPlanResponse createPlan(AIPlanRequest request) {
		// 프롬프트 템플릿 작성
		String promptText = """
				너는 여행 계획을 도와주는 AI야. 아래 조건에 따라 여행 계획을 작성해줘.

				조건:
				- 여행지: {location}
				- 시작일: {startDate}
				- 종료일: {endDate}
				- 분위기: {vibe}
				- 요청사항: {memo}

				응답 형식:
				JSON 형식으로 다음 필드를 포함해줘:
				- title (문자열)
				- startDate (문자열, yyyy-MM-dd)
				- endDate (문자열, yyyy-MM-dd)
				- day (정수)
				- attractions (배열), 각 요소는:
				  - title (문자열)
				  - areaCode (정수)
				  - siGunGuCode (정수)
				  - latitude (실수)
				  - longitude (실수)
				  - address (문자열)

				**JSON 외에 어떤 설명도 추가하지 말고, JSON만 응답해줘. 백틱도 금지.**
				""";


		PromptTemplate template = new PromptTemplate(promptText);
		Prompt prompt = template.create(request.toMap());
		String result = advisedChatClient.prompt(prompt).call().content();
		// 📌 여기서 AI 응답 원본 확인
		System.out.println("🔥 AI 응답 원본:\n" + result);
		// JSON → DTO 파싱
		try {
			AIPlanResponse plan = objectMapper.readValue(result, AIPlanResponse.class);

			// 관광지 DB 확인 or 등록
			for (var attr : plan.attractions()) {
				Attraction foundOrCreated = attractionService.findOrRegisterByName(attr.getTitle());
				attr.setAreaCode(foundOrCreated.getAreaCode());
				attr.setSiGunGuCode(foundOrCreated.getSiGunGuCode());
				attr.setLatitude(foundOrCreated.getLatitude());
				attr.setLongitude(foundOrCreated.getLongitude());
				attr.setAddress(foundOrCreated.getAddr1());
			}
			return plan;
		} catch (Exception e) {
			throw new RuntimeException("AI 응답 파싱 실패: " + e.getMessage());
		}
	}

	// AI를 통한 계획 수정
	public AIPlanResponse updatePlan(AIPlanUpdateRequest request) {
		String promptText = """
				이전에 작성된 여행 계획을 참고해서 아래 요청사항에 맞게 수정된 계획을 JSON 형식으로 다시 작성해줘.

				이전 계획:
				{previousPlan}

				요청사항:
				{userPrompt}

				응답 형식은 기존과 동일해.
				""";

		PromptTemplate template = new PromptTemplate(promptText);
		Prompt prompt = template.create(request.toMap());
		String result = advisedChatClient.prompt(prompt).call().content();

		try {
			AIPlanResponse updated = objectMapper.readValue(result, AIPlanResponse.class);

			for (var attr : updated.attractions()) {
				Attraction foundOrCreated = attractionService.findOrRegisterByName(attr.getTitle());
				attr.setAreaCode(foundOrCreated.getAreaCode());
				attr.setSiGunGuCode(foundOrCreated.getSiGunGuCode());
				attr.setLatitude(foundOrCreated.getLatitude());
				attr.setLongitude(foundOrCreated.getLongitude());
				attr.setAddress(foundOrCreated.getAddr1());
			}
			return updated;
		} catch (Exception e) {
			throw new RuntimeException("AI 응답 파싱 실패: " + e.getMessage());
		}
	}
}
