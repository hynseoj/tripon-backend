package com.ssafy.tripon.attraction.presentation;

import com.ssafy.tripon.attraction.application.AttractionCursorPage;
import com.ssafy.tripon.attraction.application.AttractionService;
import com.ssafy.tripon.attraction.application.ContentTypeResponse;
import com.ssafy.tripon.attraction.application.GugunResponse;
import com.ssafy.tripon.attraction.application.SidoResponse;
import com.ssafy.tripon.attraction.application.command.AttractionFindCommand;
import com.ssafy.tripon.attraction.presentation.request.AttractionSaveRequest;
import com.ssafy.tripon.attraction.presentation.response.AttractionCursorPageResponse;
import com.ssafy.tripon.attraction.presentation.response.ContentTypeFindAllResponse;
import com.ssafy.tripon.attraction.presentation.response.GugunFindAllResponse;
import com.ssafy.tripon.attraction.presentation.response.SidoFindAllResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attractions")
public class AttractionController {

	private final AttractionService attractionService;

	// 관광지 생성
	@PostMapping
	public ResponseEntity<Void> saveAttraction(@Valid @RequestBody AttractionSaveRequest request) {
		Integer id = attractionService.saveAttraction(request.toCommand());
		return ResponseEntity.created(URI.create("/api/v1/attractions/" + id))
				.header("Access-Control-Expose-Headers", "Location")
				.build();
	}

	// 관광지 조회
	@GetMapping
	public ResponseEntity<AttractionCursorPageResponse> findAllAttraction(
			@RequestParam(required = false) Integer areaCode,
			@RequestParam(required = false) Integer siGunGuCode,
			@RequestParam(required = false) Integer type,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer cursor,
			@RequestParam(defaultValue = "20") int size
	) {
		AttractionFindCommand command = new AttractionFindCommand(areaCode, siGunGuCode, type, keyword, cursor, size + 1);
		AttractionCursorPage response = attractionService.findAllAttractions(command);
		return ResponseEntity.ok(AttractionCursorPageResponse.from(response));
	}

	@GetMapping("/sidos")
	public ResponseEntity<SidoFindAllResponse> findAllSidos() {
		List<SidoResponse> response = attractionService.findAllSidos();
		return ResponseEntity.ok(SidoFindAllResponse.from(response));
	}

	@GetMapping("/guguns")
	public ResponseEntity<GugunFindAllResponse> findAllGuguns(@RequestParam Integer sidoCode) {
		List<GugunResponse> guguns = attractionService.findAllGuguns(sidoCode);
		return ResponseEntity.ok(GugunFindAllResponse.from(guguns));
	}

	@GetMapping("/types")
	public ResponseEntity<ContentTypeFindAllResponse> findAllContentTypes() {
		List<ContentTypeResponse> response = attractionService.findAllContentTypes();
		return ResponseEntity.ok(ContentTypeFindAllResponse.from(response));
	}
}
