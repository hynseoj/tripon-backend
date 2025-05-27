package com.ssafy.tripon.plan.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.tripon.attraction.domain.Attraction;
import com.ssafy.tripon.attraction.domain.AttractionRepository;
import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.common.exception.ErrorCode;
import com.ssafy.tripon.plan.application.message.PlanEditMessage;
import com.ssafy.tripon.plan.application.message.PlanUpdateBroadcast;
import com.ssafy.tripon.plan.domain.Plan;
import com.ssafy.tripon.plan.domain.PlanEvent;
import com.ssafy.tripon.plan.domain.PlanEventRepository;
import com.ssafy.tripon.plan.domain.PlanRepository;
import com.ssafy.tripon.plandetail.domain.PlanAttraction;
import com.ssafy.tripon.plandetail.domain.PlanAttractionRepository;
import com.ssafy.tripon.plandetail.domain.PlanDetailRepository;
import com.ssafy.tripon.plandetail.domain.PlanDetail;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanCollabService {

	private final PlanRepository planRepository;
	private final PlanDetailRepository planDetailRepository;
	private final PlanAttractionRepository planAttractionRepository;
	private final PlanEventRepository planEventRepository;
	private final AttractionRepository attractionRepository;

	private final ObjectMapper objectMapper;

	public PlanUpdateBroadcast applyEvent(PlanEditMessage message) throws JsonProcessingException {
        // 이벤트 저장
        PlanEvent planEvent = new PlanEvent(message.planId(), message.email(), message.eventType(), message.payload());
        planEventRepository.insertEvent(planEvent);

        // 현재 버전 조회
        long currentVersion = planRepository.selectVersion(message.planId());
        System.out.println("currentVersion = " + currentVersion);

        // 이벤트 타입별 처리
        Plan plan = planRepository.findPlanById(message.planId());
        int updated = 0;

        System.out.println("==========payload===========");
        System.out.println(message.payload());
        String broadcastPayload = message.payload(); // 기본 페이로드

        System.out.println(message.eventType());

        Map<String, Object> payload = objectMapper.readValue(message.payload(), new TypeReference<>() {});
        switch (message.eventType()) {
            case "UPDATE_TITLE": {
                updated = planRepository.updateTitleAndMemoWithVersion(
                        plan.getId(),
                        payload.get("newTitle").toString(),
                        plan.getMemo(),
                        currentVersion
                );
                break;
            }
            case "UPDATE_MEMO": {
                System.out.println("들어는 옴;;");
                System.out.println("plan = " + plan.getId());
                System.out.println("memo = " + payload.get("newMemo").toString());
                updated = planRepository.updateTitleAndMemoWithVersion(
                        plan.getId(),
                        plan.getTitle(),
                        payload.get("newMemo").toString(),
                        currentVersion
                );
                break;
            }
            case "ADD_ATTRACTION": {
                int day = Integer.parseInt(payload.get("day").toString());
                int attractionId = Integer.parseInt(payload.get("attractionId").toString());

                System.out.println(plan.getId());
                PlanDetail planDetail = planDetailRepository.findByPlanIdAndDay(plan.getId(), day);
                PlanAttraction planAttraction=planAttractionRepository.findPlanAttraction(planDetail.getId(), attractionId);
                
                planAttractionRepository.savePlanAttraction(planAttraction);
                int generatedId = planAttraction.getId();
                updated++;

                // 추가된 관광지 정보 조회
                Attraction attraction = attractionRepository.findAttractionById(attractionId);

                // 브로드캐스트 페이로드 재구성
                Map<String, Object> map = new HashMap<>();
                map.put("generatedId", generatedId);
                map.put("day", day);
                map.put("attractionId", attractionId);
                map.put("title", attraction.getTitle());
                map.put("address", attraction.getAddr1());
                map.put("imageUrl", attraction.getFirstImage1());
                map.put("latitude", attraction.getLatitude());
                map.put("longitude", attraction.getLongitude());
                broadcastPayload = objectMapper.writeValueAsString(map);
                break;
            }
            case "REMOVE_ATTRACTION": {
                int day = Integer.parseInt(payload.get("day").toString());
                int planDetailId = planDetailRepository.findByPlanIdAndDay(plan.getId(), day).getId();
                int attractionId = Integer.parseInt(payload.get("attractionId").toString());
                planAttractionRepository.deletePlanAttractionByPlanDetailIdAndAttractionId(planDetailId, attractionId);
                updated = 1;

                // 브로드캐스트에 삭제 정보만 포함
                Map<String, Object> map = new HashMap<>();
                map.put("attractionId", attractionId);
                map.put("day", day);
                broadcastPayload = objectMapper.writeValueAsString(map);
                break;
            }
            case "REORDER_ATTRACTIONS": {
                int day = Integer.parseInt(payload.get("day").toString());
                List<Integer> attractionIds = ((List<?>) payload.get("attractions"))
                        .stream()
                        .map(o -> Integer.parseInt(o.toString()))
                        .toList();

                int planDetailId = planDetailRepository
                        .findByPlanIdAndDay(plan.getId(), day)
                        .getId();

                planAttractionRepository.deletePlanAttractionByPlanDetailId(planDetailId);

                int count = 0;
                for(int i = 0; i < attractionIds.size(); i++) {
                	PlanAttraction pa = new PlanAttraction(planDetailId, attractionIds.get(i), i);
                    planAttractionRepository.savePlanAttraction(pa);
                    count++;
                }

                updated = count;
                break;
            }
            default:
                throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }

        // 낙관적 락 실패 처리
        if ( (message.eventType().startsWith("UPDATE") && updated == 0) ) {
            throw new OptimisticLockingFailureException("다른 사용자가 계획을 편집했습니다.");
        }

        // 브로드캐스트용 버전 재조회
        long newVersion = planRepository.selectVersion(plan.getId());

        return new PlanUpdateBroadcast(
                plan.getId(),
                message.eventType(),
                broadcastPayload,
                newVersion
        );
    }
}
