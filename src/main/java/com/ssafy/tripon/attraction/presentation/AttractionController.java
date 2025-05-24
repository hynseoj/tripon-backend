package com.ssafy.tripon.attraction.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.tripon.attraction.application.AttractionService;
import com.ssafy.tripon.attraction.application.command.AttractionFindCommand;
import com.ssafy.tripon.attraction.presentation.request.AttractionSaveRequest;
import com.ssafy.tripon.attraction.presentation.response.AttractionFindAllResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attractions")
public class AttractionController {
	private final AttractionService attractionService;

	// 관광지 생성
	@PostMapping
	public ResponseEntity<Void> saveAttraction(@Valid @RequestBody AttractionSaveRequest request) {
		Integer id = attractionService.saveAttraction(request.toCommand()) == null ? 0
				: attractionService.saveAttraction(request.toCommand());
		return ResponseEntity.created(URI.create("/api/v1/attractions/" + id)).build();
	}

	// 관광지 조회
	@GetMapping
	public ResponseEntity<AttractionFindAllResponse> findAllAttraction(@RequestParam(required = false) Integer areaCode,
			@RequestParam(required = false) Integer siGunGuCode, @RequestParam(required = false) String keyword) {
		AttractionFindCommand command = new AttractionFindCommand(areaCode, siGunGuCode, keyword);
		AttractionFindAllResponse response = new AttractionFindAllResponse(
				attractionService.findAllAttractions(command));
		
		return ResponseEntity.ok(response);
	}

}
