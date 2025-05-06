package com.ssafy.tripon.attraction.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.tripon.attraction.application.command.AttractionFindCommand;
import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;
import com.ssafy.tripon.attraction.domain.Attraction;
import com.ssafy.tripon.attraction.domain.AttractionRepository;
import com.ssafy.tripon.attraction.domain.CustomAttraction;
import com.ssafy.tripon.attraction.domain.CustomAttractionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {

	private final AttractionRepository attractionRepository;
	private final CustomAttractionRepository customAttractionRepository;

	// 관광지 생성
	public Integer saveAttraction(AttractionSaveCommand command) {
		// attraction에 있는지 확인
		if (attractionRepository.findAttraction(command) != null) {
			return null;

		} // custom attraction에 있는지 확인
		else if (customAttractionRepository.findCustomAttraction(command) != null) {
			return null;
		}

		CustomAttraction customAttraction = command.toCustomAttraction();
		// 없으면 save
		return customAttractionRepository.saveCustomAttraction(customAttraction);
	}

	// 관광지 조회
	public List<AttractionServiceResponse> findAllAttractions(AttractionFindCommand command) {
		// 각 테이블에서 엔티티 목록 조회
		List<Attraction> attractions = attractionRepository.findAllAttraction(command);
		List<CustomAttraction> customAttractions = customAttractionRepository.findAllCustomAttraction(command);

		// 각각 toResponse()로 변환
		List<AttractionServiceResponse> list = new ArrayList<>();
		list.addAll(attractions.stream().map(a -> (AttractionServiceResponse.from(a))).toList());
		list.addAll(customAttractions.stream().map(c -> (AttractionServiceResponse.from(c))).toList());

		return list;
	}

}
