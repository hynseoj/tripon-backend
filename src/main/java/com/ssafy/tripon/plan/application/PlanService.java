package com.ssafy.tripon.plan.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.tripon.plan.application.command.PlanSaveCommand;
import com.ssafy.tripon.plan.application.command.PlanUpdateCommand;
import com.ssafy.tripon.plan.domain.Plan;
import com.ssafy.tripon.plan.domain.PlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

	private final PlanRepository planRepository;

	public Integer savePlan(PlanSaveCommand command) {
		Plan plan = command.toPlan();
		planRepository.savePlan(plan);
		return plan.getId();
	}

	public List<PlanServiceResponse> findAllPlanByMemberId(String memberId) {
		return planRepository.findAllPlanByMemberId(memberId).stream().map(p -> (PlanServiceResponse.from(p))).toList();
	}

	public PlanServiceResponse findPlanById(int id) {
		Plan plan = planRepository.findPlanById(id);
		return PlanServiceResponse.from(plan);   
	} 

	public void updatePlanById(PlanUpdateCommand command) {
		planRepository.updatePlan(command.toPlan());
	}

	public void deletePlanById(Integer id) {
		planRepository.deletePlanById(id);
	}

}
