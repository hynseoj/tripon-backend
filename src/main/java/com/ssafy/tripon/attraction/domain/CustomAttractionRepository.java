package com.ssafy.tripon.attraction.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.tripon.attraction.application.command.AttractionFindCommand;
import com.ssafy.tripon.attraction.application.command.AttractionSaveCommand;

@Mapper
public interface CustomAttractionRepository {

	// 관광지 조건 조회
	List<CustomAttraction> findAllCustomAttraction(AttractionFindCommand command);

	// 관광지 단건 조회
	Integer findCustomAttraction(AttractionSaveCommand command);
	
	// 관광지 삽입
	Integer saveCustomAttraction(CustomAttraction attraction);

}
