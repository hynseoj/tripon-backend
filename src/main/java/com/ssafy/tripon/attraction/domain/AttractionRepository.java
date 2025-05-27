package com.ssafy.tripon.attraction.domain;

import com.ssafy.tripon.attraction.application.command.AttractionFindCommand;
import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionRepository {
	
	// 관광지 조건 조회
	List<Attraction> findAllAttraction(AttractionFindCommand command);
	
	// 관광지 단건 조회 - 타이틀
	Integer findAttraction(AttractionSaveCommand command);

	// 관광지 단건 조회 - 타이틀 + 주소
	Attraction findUniqueAttraction(AttractionSaveCommand command);

	// 관광지 삽입
	Integer saveCustomAttraction(CustomAttraction attraction);

	// 관광지 단건 조회 - 아이디
	Attraction findAttractionById(Integer id);
}
