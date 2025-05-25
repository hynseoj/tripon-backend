package com.ssafy.tripon.plan.presentation;

import com.ssafy.tripon.common.auth.config.LoginMember;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.plan.application.PlanService;
import com.ssafy.tripon.plan.application.PlanServiceResponse;
import com.ssafy.tripon.plan.application.command.PlanUpdateCommand;
import com.ssafy.tripon.plan.presentation.request.PlanSaveRequest;
import com.ssafy.tripon.plan.presentation.request.PlanUpdateRequest;
import com.ssafy.tripon.plan.presentation.response.PlanFindAllByMemberIdResponse;
import com.ssafy.tripon.plan.presentation.response.PlanFindByIdResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
public class PlanController {

	private final PlanService planService;
	
	// 계획 생성
	@PostMapping
	public ResponseEntity<Void> savePlan(@LoginMember Member member, @Valid @RequestBody PlanSaveRequest request) {
		Integer id = planService.savePlan(request.toCommand(member.getEmail()));
		return  ResponseEntity.created(URI.create("/api/v1/plans/" + id))
				.header("Access-Control-Expose-Headers", "Location")
				.build();
	}

	// 계획 전체 조회
	@GetMapping
	public ResponseEntity<PlanFindAllByMemberIdResponse> findAllPlanByMemberId(@LoginMember Member member) {
		List<PlanServiceResponse> serviceList = planService.findAllPlanByMemberId(member.getEmail());

		PlanFindAllByMemberIdResponse response = new PlanFindAllByMemberIdResponse(serviceList);
		return ResponseEntity.ok(response);
	}

	// 계획 상세 조회
	@GetMapping("/{planId}")
	public ResponseEntity<PlanFindByIdResponse> findPlanById(@PathVariable int planId) {
		PlanServiceResponse response = planService.findPlanById(planId);
		return ResponseEntity.ok(PlanFindByIdResponse.from(response));
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
