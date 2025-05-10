package com.ssafy.tripon.attraction.application;

import com.ssafy.tripon.attraction.application.command.AttractionFindCommand;
import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;
import com.ssafy.tripon.attraction.domain.Attraction;
import com.ssafy.tripon.attraction.domain.AttractionRepository;
import com.ssafy.tripon.attraction.domain.CustomAttraction;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttractionService {

	private final AttractionRepository attractionRepository;

	// 관광지 생성
	public Integer saveAttraction(AttractionSaveCommand command) {
		// attractions+custom_attractions에 있는지 확인
		if (attractionRepository.findAttraction(command) != null) {
			return attractionRepository.findAttraction(command);
		}

		CustomAttraction customAttraction = command.toCustomAttraction();
		attractionRepository.saveCustomAttraction(customAttraction);
		
		// 없으면 save
		return customAttraction.getId();
	}

	// 관광지 조회
	public List<AttractionServiceResponse> findAllAttractions(AttractionFindCommand command) {
		// 각 테이블에서 엔티티 목록 조회
		List<Attraction> attractions = attractionRepository.findAllAttraction(command);

		// 각각 toResponse()로 변환
		List<AttractionServiceResponse> list = new ArrayList<>();
		list.addAll(attractions.stream().map(AttractionServiceResponse::from).toList());

		return list;
	}

}
