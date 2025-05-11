package com.ssafy.tripon.plan.presentation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tripon.plan.application.PlanService;
import com.ssafy.tripon.plan.application.PlanServiceResponse;
import com.ssafy.tripon.plan.application.command.PlanUpdateCommand;
import com.ssafy.tripon.plan.presentation.request.PlanSaveRequest;
import com.ssafy.tripon.plan.presentation.request.PlanUpdateRequest;
import com.ssafy.tripon.plan.presentation.response.PlanFindAllByMemberIdResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
public class PlanController {

	private final PlanService planService;
	// member 정보 임의 설정
	private String memberId = "admin@ssafy.com";
	
	// 계획 생성
	@PostMapping
	public ResponseEntity<Void> savePlan(@Valid @RequestBody PlanSaveRequest request) {
		Integer id = planService.savePlan(request.toCommand(memberId));
		return  ResponseEntity.created(URI.create("/api/v1/plans" + id)).build();
	}

	// 계획 전체 조회
	@GetMapping
	public ResponseEntity<PlanFindAllByMemberIdResponse> findAllPlanByMemberId() {
		List<PlanServiceResponse> serviceList = planService.findAllPlanByMemberId(memberId);

		PlanFindAllByMemberIdResponse response = new PlanFindAllByMemberIdResponse(serviceList);
		return ResponseEntity.ok(response);
	}

	// 계획 상세 조회
	@GetMapping("/{planId}")
	public ResponseEntity<PlanServiceResponse> findPlanById(@PathVariable int planId) {
		PlanServiceResponse response = planService.findPlanById(planId);
		return ResponseEntity.ok(response);
	}

	// 계획 수정
	@PutMapping("/{planId}")
	public ResponseEntity<Void> updatePlanById(@PathVariable Integer planId, @Valid @RequestBody PlanUpdateRequest req) {
		PlanUpdateCommand command = req.toCommand(planId);
		planService.updatePlanById(command);
		return  ResponseEntity.created(URI.create("/api/v1/plans" + planId)).build();
	}

	// 계획 삭제
	@DeleteMapping("/{planId}")
	public ResponseEntity<Integer> deletePlanById(@PathVariable int planId) {
		planService.deletePlanById(planId);
		return ResponseEntity.ok(planId);
	}
	
}
