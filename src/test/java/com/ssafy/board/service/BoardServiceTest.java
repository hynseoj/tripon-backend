package com.ssafy.board.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(properties = { "spring.config.location=classpath:application.properties" })
class BoardServiceTest {

	@Autowired
	private BoardService boardService;

	@Test
	@Disabled
	public void testWriteArticle() {
		BoardDto boardDto = new BoardDto();
		boardDto.setUserId("ssafy");
		boardDto.setSubject(
				"spring boot test 제목입니다!!!");
		boardDto.setContent("spring boot test 내용입니다!!!");
		try {
			boardService.writeArticle(boardDto);
		} catch (Exception e) {
			fail("글 작성 실패!!");
		}
	}

	@Test
	@Disabled
	public void testListArticle() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pgno", "1");
		map.put("key", "");
		map.put("word", "");
		try {
			List<BoardDto> list = boardService.listArticle(map);
			assertThat(list.size()).isEqualTo(20);
//			assertEquals(20, list.size());
			log.debug("list : {}", list);
			log.debug("list size : {}", list.size());
		} catch (Exception e) {
			fail("글 목록 실패!!");
		}

	}

	@Test
//	@Disabled
	public void testGetArticle() {
		int no = 10;
		try {
			BoardDto boardDto = boardService.getArticle(no);
			assertThat(boardDto).isNotNull();
//			assertNotNull(boardDto, no + "글 없음");
		} catch (Exception e) {
			fail(no + "번 글 얻기 실패!!");
		}

	}

}
