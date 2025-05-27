package com.ssafy.tripon.plan.presentation;

import com.ssafy.tripon.common.auth.config.LoginMember;
import com.ssafy.tripon.common.exception.CustomException;
import com.ssafy.tripon.common.exception.ErrorCode;
import com.ssafy.tripon.member.domain.Member;
import com.ssafy.tripon.plan.application.PlanService;
import com.ssafy.tripon.plan.application.PlanServiceResponse;
import com.ssafy.tripon.plan.application.PlanShareService;
import com.ssafy.tripon.plan.application.command.PlanUpdateCommand;
import com.ssafy.tripon.plan.presentation.request.PlanSaveRequest;
import com.ssafy.tripon.plan.presentation.request.PlanUpdateRequest;
import com.ssafy.tripon.plan.presentation.response.LinkCreateResponse;
import com.ssafy.tripon.plan.presentation.response.PlanFindAllByMemberIdResponse;
import com.ssafy.tripon.plan.presentation.response.PlanFindByIdResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
public class PlanController {

	private final PlanService planService;
	private final PlanShareService planShareService;
	
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
	public ResponseEntity<PlanFindAllByMemberIdResponse> findAllPlanByMemberId(
	        @LoginMember Member member,
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "5") int size
	) {
		PlanFindAllByMemberIdResponse res = planService.findPlans(member.getEmail(), page, size);
		System.out.println(res);
	    return ResponseEntity.ok(planService.findPlans(member.getEmail(), page, size));
	}

	// 계획 상세 조회
	@GetMapping("/{planId}")
	public ResponseEntity<PlanFindByIdResponse> findPlanById(
			@LoginMember Member member,
			@PathVariable int planId
	) {
		if (!planService.isOwner(planId, member.getEmail())) throw new CustomException(ErrorCode.PLANS_NOT_FOUND);
		PlanServiceResponse response = planService.findPlanById(planId);
		return ResponseEntity.ok(PlanFindByIdResponse.from(response));
	}

	// 계획 수정
	@PutMapping("/{planId}")
	public ResponseEntity<Void> updatePlanById(
			@LoginMember Member member,
			@PathVariable Integer planId,
			@Valid @RequestBody PlanUpdateRequest req,
			@RequestParam(required = false) String token
	) {
		if (member != null && planService.isOwner(planId, member.getEmail())) {
			PlanUpdateCommand command = req.toCommand(planId);
			planService.updatePlanById(command);
		}
		else {
			PlanServiceResponse response = planShareService.validateToken(token);
			if (!response.planId().equals(planId)) throw new CustomException(ErrorCode.PLANS_NOT_FOUND);
			PlanUpdateCommand command = req.toCommand(planId);
			planService.updatePlanById(command);
		}
		return  ResponseEntity.created(URI.create("/api/v1/plans" + planId)).build();
	}

	// 계획 삭제
	@DeleteMapping("/{planId}")
	public ResponseEntity<Integer> deletePlanById(@PathVariable int planId) {
		planService.deletePlanById(planId);
		return ResponseEntity.ok(planId);
	}


	@PostMapping("/{planId}/share")
	public ResponseEntity<LinkCreateResponse> createShareLink(@PathVariable Integer planId) {
		String token = planShareService.createShareLink(planId);
		String url = "http://localhost:5173/plans/" + planId + "/share/" + token; // @Todo: 배포한다면, 배포된 url로 변경
		return ResponseEntity.ok(LinkCreateResponse.from(url));
	}

	@GetMapping("/{planId}/share/{token}")
	public ResponseEntity<PlanFindByIdResponse> findPlanByShareLink(@PathVariable String token) {
		PlanServiceResponse response = planShareService.validateToken(token);
		return ResponseEntity.ok(PlanFindByIdResponse.from(response));
	}
}
