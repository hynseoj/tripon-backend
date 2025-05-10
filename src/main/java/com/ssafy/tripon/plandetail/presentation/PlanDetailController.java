package com.ssafy.tripon.plandetail.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tripon.plandetail.application.PlanDetailService;
import com.ssafy.tripon.plandetail.presentation.request.PlanDetailSaveRequest;
import com.ssafy.tripon.plandetail.presentation.request.PlanDetailUpdateRequest;
import com.ssafy.tripon.plandetail.presentation.response.PlanDetailFindByIdResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/plans/{planId}")
@RequiredArgsConstructor
public class PlanDetailController {
	
	private final PlanDetailService planDetailService;
	
	@PostMapping
	public ResponseEntity<Void> savePlanDetail(@PathVariable Integer planId, @RequestBody PlanDetailSaveRequest request) {
		Integer id = planDetailService.savePlanDetail(request.toCommand(planId));
		return ResponseEntity.created(URI.create("/api/v1/plans/" + planId + "/" + id)).build();
	}
	
	@GetMapping("/{planDetailId}")
	public ResponseEntity<PlanDetailFindByIdResponse> findPlanDetailById(@PathVariable Integer planId, @PathVariable Integer planDetailId) {
		PlanDetailFindByIdResponse response = PlanDetailFindByIdResponse.from(planDetailService.findPlanDetailById(planDetailId));
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{planDetailId}")
	public ResponseEntity<Void> updatePlanDetail(@PathVariable Integer planId, @PathVariable Integer planDetailId, @RequestBody PlanDetailUpdateRequest request) {
		planDetailService.updatePlanDetail(request.toCommand(planDetailId));
		return ResponseEntity.created(URI.create("/api/v1/plans/" + planId + "/" + planDetailId)).build();
	}
	
	@DeleteMapping("/{planDetailId}")
	public ResponseEntity<Void> deletePlanDetail(@PathVariable Integer planDetailId) {
		planDetailService.deletePlanDetail(planDetailId);
		return ResponseEntity.noContent().build();
	}
}
