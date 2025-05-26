package com.ssafy.tripon.plandetail.application;

import com.ssafy.tripon.attraction.application.AttractionService;
import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.common.exception.ErrorCode;
import com.ssafy.tripon.plandetail.application.command.PlanDetailSaveCommand;
import com.ssafy.tripon.plandetail.application.command.PlanDetailUpdateCommand;
import com.ssafy.tripon.plandetail.domain.PlanAttraction;
import com.ssafy.tripon.plandetail.domain.PlanAttractionRepository;
import com.ssafy.tripon.plandetail.domain.PlanDetail;
import com.ssafy.tripon.plandetail.domain.PlanDetailRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		Integer planDetailId = planDetail.getId();

		for (Integer attractionId : command.attractions()) {
			planAttractionRepository.savePlanAttraction(new PlanAttraction(planDetailId, attractionId));
		}

		return planDetailId;
	}

	// N일차 계획 상세 조회
	public PlanDetailServiceResponse findPlanDetailById(Integer planDetailId) {
		return Optional.ofNullable(planDetailRepository.findPlanDetailById(planDetailId))
				.orElseThrow(() -> new CustomException(ErrorCode.PLANDETAILS_NOT_FOUND));
	}

	// N일차 계획 수정
	public void updatePlanDetail(PlanDetailUpdateCommand command) {
		Integer planDetailId = command.id();
		
		// 해당 id를 가진 planDetail이 있는지 검증
		Optional.ofNullable(planDetailRepository.findPlanDetailById(planDetailId))
		.orElseThrow(() -> new CustomException(ErrorCode.PLANDETAILS_NOT_FOUND));
		
		// attraction 삭제
		planAttractionRepository.deletePlanAttractionByPlanDetailId(planDetailId);

		for (Integer attractionId : command.attractions()) {
			planAttractionRepository.savePlanAttraction(new PlanAttraction(planDetailId, attractionId));
		}
	}

	// N일차 계획 삭제
	public void deletePlanDetail(Integer planDetailId) {

		// attraction 삭제
		planAttractionRepository.deletePlanAttractionByPlanDetailId(planDetailId);
		
		// N일차 계획 삭제
		int result = planDetailRepository.deletePlanDetail(planDetailId);

		if(result == 0) {
			throw new CustomException(ErrorCode.PLANDETAILS_NOT_FOUND);
		}
	}
}
