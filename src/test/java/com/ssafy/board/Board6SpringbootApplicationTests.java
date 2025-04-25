package com.ssafy.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@SpringBootTest(properties = { "userid=ssafy", "userpwd=1234" })

@SpringBootTest(properties = { "spring.config.location=classpath:application.properties" })

//@SpringBootTest(classes = { BoardController.class, WebMvcConfigurer.class })
class Board6SpringbootApplicationTests {

	@Value("${userid}")
	private String userId;

	@Value("${userpwd}")
	private String userPwd;

	@Test
	@DisplayName("###### 프로퍼티 읽기 테스트 ######")
	void contextLoads() {
		log.debug("userId : {}, userPwd : {}", userId, userPwd);
		assertEquals(userId, "ssafy2");
	}

}
