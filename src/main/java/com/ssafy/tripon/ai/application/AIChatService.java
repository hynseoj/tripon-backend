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
import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AIChatService {

	private final ObjectMapper objectMapper;
	private final AttractionService attractionService;
	private final ChatClient advisedChatClient;

	public AIPlanResponse createPlan(AIPlanRequest request) {
		System.out.println("처음 요청했습니다");
		String promptText = """
					너는 여행 계획을 도와주는 AI야. 아래 조건에 따라 여행 계획을 작성해줘.

					조건:
					- 시작일: {startDate}
					- 종료일: {endDate}
					- 선호 스타일: {like}
					- 비선호 스타일: {dislike}
					- 기타 요청사항: {etc}

					응답 형식:
					JSON 형식으로 다음 필드를 포함해줘:
					- title (문자열)
					- startDate (문자열, yyyy-MM-dd)
					- endDate (문자열, yyyy-MM-dd)
					- days (배열), 각 요소는:
					  - day (정수)
					  - attractions (배열), 각 요소는:
					    - title (문자열)
					    - latitude (실수)
					    - longitude (실수)
					    - address (문자열)
					    - imageUrl (문자열)
					    - id (정수)

					**JSON 외에 어떤 설명도 추가하지 말고, JSON만 응답해줘. 백틱도 금지.**
				""";

		PromptTemplate template = new PromptTemplate(promptText);
		Prompt prompt = template.create(request.toMap());
		String result = advisedChatClient.prompt(prompt).call().content();
		System.out.println("🔥 AI 응답 원본:\n" + result);

		try {
			AIPlanResponse plan = objectMapper.readValue(result, AIPlanResponse.class);

			// ✅ 각 일차별 관광지들 순회
			for (var day : plan.days()) {
				for (var attr : day.attractions()) {
					Attraction foundOrCreated = attractionService.findOrRegisterByName(new AttractionSaveCommand(
							attr.getTitle().trim(), attr.getAddress().trim(), attr.getLatitude(), attr.getLongitude()));
					attr.setId(foundOrCreated.getNo());
					attr.setLatitude(foundOrCreated.getLatitude());
					attr.setLongitude(foundOrCreated.getLongitude());
					attr.setAddress(foundOrCreated.getAddr1());
					attr.setAreaCode(foundOrCreated.getAreaCode());
					attr.setSiGunGuCode(foundOrCreated.getSiGunGuCode());
					attr.setImageUrl(foundOrCreated.getFirstImage1());
				}
			}

			return plan;
		} catch (Exception e) {
			throw new RuntimeException("AI 응답 파싱 실패: " + e.getMessage());
		}
	}

	public AIPlanResponse updatePlan(AIPlanUpdateRequest request) {
		System.out.println("처음 요청이 아닙니다.");
		String promptText = """
					이전에 작성된 여행 계획을 참고해서 아래 요청사항에 맞게 수정된 계획을 JSON 형식으로 다시 작성해줘.

					요청사항:
					- 선호 스타일: {like}
					- 비선호 스타일: {dislike}
					- 기타 요청사항: {etc}
					
					응답 형식:
					JSON 형식으로 다음 필드를 포함해줘:
					- title (문자열)
					- startDate (문자열, yyyy-MM-dd)
					- endDate (문자열, yyyy-MM-dd)
					- days (배열), 각 요소는:
					  - day (정수)
					  - attractions (배열), 각 요소는:
					    - title (문자열)
					    - latitude (실수)
					    - longitude (실수)
					    - address (문자열)
					    - imageUrl (문자열)
					    - id (정수)
					
					**JSON 외에 어떤 설명도 추가하지 말고, JSON만 응답해줘. 백틱도 금지.**
				""";

		PromptTemplate template = new PromptTemplate(promptText);
		Prompt prompt = template.create(request.toMap());
		String result = advisedChatClient.prompt(prompt).call().content();
		System.out.println("업데이트 응답 원본: " + result);
		try {
			AIPlanResponse updated = objectMapper.readValue(result, AIPlanResponse.class);

			for (var day : updated.days()) {
				for (var attr : day.attractions()) {
					Attraction foundOrCreated = attractionService.findOrRegisterByName(new AttractionSaveCommand(
							attr.getTitle().trim(), attr.getAddress().trim(), attr.getLatitude(), attr.getLongitude()));
					attr.setId(foundOrCreated.getNo());
					attr.setLatitude(foundOrCreated.getLatitude());
					attr.setLongitude(foundOrCreated.getLongitude());
					attr.setAddress(foundOrCreated.getAddr1());
					attr.setAreaCode(foundOrCreated.getAreaCode());
					attr.setSiGunGuCode(foundOrCreated.getSiGunGuCode());
					attr.setImageUrl(foundOrCreated.getFirstImage1());
				}
			}

			return updated;
		} catch (Exception e) {
			throw new RuntimeException("AI 응답 파싱 실패: " + e.getMessage());
		}
	}
}
