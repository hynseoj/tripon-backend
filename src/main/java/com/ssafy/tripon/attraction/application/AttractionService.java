package com.ssafy.tripon.attraction.application;

import com.ssafy.tripon.attraction.application.command.AttractionFindCommand;
import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;
import com.ssafy.tripon.attraction.domain.Attraction;
import com.ssafy.tripon.attraction.domain.AttractionRepository;
import com.ssafy.tripon.attraction.domain.ContentType;
import com.ssafy.tripon.attraction.domain.ContentTypeRepository;
import com.ssafy.tripon.attraction.domain.CustomAttraction;
import com.ssafy.tripon.attraction.domain.Gugun;
import com.ssafy.tripon.attraction.domain.GugunRepository;
import com.ssafy.tripon.attraction.domain.Sido;
import com.ssafy.tripon.attraction.domain.SidoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttractionService {

	private final AttractionRepository attractionRepository;
	private final SidoRepository sidoRepository;
	private final GugunRepository gugunRepository;
	private final ContentTypeRepository contentTypeRepository;

	// 관광지 생성
	public Integer saveAttraction(AttractionSaveCommand command) {
		// attractions+custom_attractions에 있는지 확인
		Integer foundId = attractionRepository.findAttraction(command);
		if (foundId != null) {
			return -1;
		}
		CustomAttraction customAttraction = command.toCustomAttraction();
		attractionRepository.saveCustomAttraction(customAttraction);

		// 없으면 save
		return customAttraction.getId();
	}

	// 관광지 조회
	public AttractionCursorPage findAllAttractions(AttractionFindCommand command) {
		// 각 테이블에서 엔티티 목록 조회
		List<Attraction> attractions = attractionRepository.findAllAttraction(command);
		boolean hasNext = attractions.size() > command.size() - 1;
		Integer nextCursor = null;

		if (hasNext) {
			attractions.remove(attractions.size() - 1);
			nextCursor = attractions.get(attractions.size() - 1).getNo();
		}

		List<AttractionServiceResponse> content = attractions.stream().map(AttractionServiceResponse::from).toList();
		return AttractionCursorPage.of(content, nextCursor);
	}

	public List<SidoResponse> findAllSidos() {
		List<Sido> sidos = sidoRepository.findAllSidos();
		return sidos.stream().map(SidoResponse::from).toList();
	}

	public List<GugunResponse> findAllGuguns(Integer sidoCode) {
		List<Gugun> guguns = gugunRepository.findAllGuguns(sidoCode);
		return guguns.stream().map(GugunResponse::from).toList();
	}

	public List<ContentTypeResponse> findAllContentTypes() {
		List<ContentType> types = contentTypeRepository.findAllContentType();
		return types.stream().map(ContentTypeResponse::from).toList();
	}

	public Attraction findOrRegisterByName(AttractionSaveCommand command) {
		List<Attraction> attractions = attractionRepository.findUniqueAttraction(command);

		// 2. 이미 존재하면 DB에서 전체 정보 조회 후 반환
		if (attractions != null && !attractions.isEmpty()) {
			System.out.println(attractions.size() +  " " + attractions.get(0).getTitle());
			return attractions.get(0);
		}

		// 3. 존재하지 않으면 나중에 저장
		return new Attraction(command.title());
	}

}
