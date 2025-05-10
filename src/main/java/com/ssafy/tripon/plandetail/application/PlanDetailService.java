package com.ssafy.tripon.plandetail.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.tripon.attraction.application.AttractionService;
import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;
import com.ssafy.tripon.plandetail.application.command.PlanDetailSaveCommand;
import com.ssafy.tripon.plandetail.application.command.PlanDetailUpdateCommand;
import com.ssafy.tripon.plandetail.domain.PlanAttraction;
import com.ssafy.tripon.plandetail.domain.PlanAttractionRepository;
import com.ssafy.tripon.plandetail.domain.PlanDetail;
import com.ssafy.tripon.plandetail.domain.PlanDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanDetailService {
	
	private final PlanDetailRepository planDetailRepository;
	private final PlanAttractionRepository planAttractionRepository;
	private final AttractionService attractionService;
	
	// N일차 계획 생성
	public Integer savePlanDetail(PlanDetailSaveCommand command) {
		PlanDetail planDetail = command.toPlanDetail();
		planDetailRepository.savePlanDetail(planDetail);
		Integer planDetailId =  planDetail.getId();
		for(AttractionSaveCommand attractionCommand : command.attractions()) {
			// 없으면 생성
			Integer attractionId = attractionService.saveAttraction(attractionCommand);
			planAttractionRepository.savePlanAttraction(new PlanAttraction(planDetailId, attractionId));
		}
		
		return planDetailId;
	}
	
	// N일차 계획 상세 조회
	public PlanDetailServiceResponse findPlanDetailById (Integer planDetailId) {
		return planDetailRepository.findPlanDetailById(planDetailId);
	}
	
	// N일차 계획 수정
	public void updatePlanDetail(PlanDetailUpdateCommand command) {
		Integer planDetailId = command.id();
		// attraction 삭제
		planAttractionRepository.deletePlanAttractionByPlanDetailId(planDetailId);
		
		for(AttractionSaveCommand attractionCommand : command.attractions()) {
			// 없으면 생성
			Integer attractionId = attractionService.saveAttraction(attractionCommand);
			planAttractionRepository.savePlanAttraction(new PlanAttraction(planDetailId, attractionId));
		}
	}
	
	// N일차 계획 삭제
	public void deletePlanDetail(Integer planDetailId) {

		// attraction 삭제
		planAttractionRepository.deletePlanAttractionByPlanDetailId(planDetailId);
		
		// N일차 계획 삭제
		planDetailRepository.deletePlanDetail(planDetailId);
	
	}
}
