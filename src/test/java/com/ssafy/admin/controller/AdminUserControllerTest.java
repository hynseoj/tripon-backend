package com.ssafy.admin.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@AutoConfigureMockMvc
@SpringBootTest(
		properties = { 
			"spring.config.location=classpath:application.properties" 
		},
		classes = { AdminUserController.class }
	)
@Slf4j
@ComponentScan(basePackages = {"com.ssafy"})
//@ContextConfiguration(classes = AdminUserController.class)
class AdminUserControllerTest {
	
	@Value("${userid}")
	private String userId;
	
	@Value("${userpwd}")
	private String userPwd;
	
	@Value("${username}")
	private String userName;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private MemberService memberService;

	@Test
//	@Disabled
	@DisplayName("##### 회원 목록 테스트 #####")
	void testUserList() throws Exception {
		log.debug("##### 회원 목록 테스트 시작 #####");
		mockMvc.perform(get("/admin/user"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andDo(print());
		log.debug("##### 회원 목록 테스트 종료 #####");
	}

	@Test
	@Disabled
	@DisplayName("##### 회원 등록 테스트 #####")
	@Transactional
	void testUserRegister() throws Exception {
		log.debug("##### 회원 등록 테스트 시작 #####");
		
		MemberDto memberDto = new MemberDto();
		memberDto.setUserId("unittest");
		memberDto.setUserName("유닛이야");
		memberDto.setUserPwd("7777");
		memberDto.setEmailId("unittest");
		memberDto.setEmailDomain("google.com");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(memberDto);
		
		mockMvc.perform(post("/admin/user").content(content).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andDo(print());
		log.debug("##### 회원 등록 테스트 종료 #####");
	}

	@Test
	@Disabled
	@DisplayName("##### 회원 정보 얻기 테스트 #####")
	void testUserInfo() throws Exception {
		log.debug("##### 회원 정보 얻기 테스트 시작 #####");
		mockMvc.perform(get("/admin/user/" + userId)) // {userId:ssafy, userPwd: 1234, userName:김싸피, ....}
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.userPwd", is(userPwd)))
			.andDo(print());
		log.debug("##### 회원 정보 얻기 테스트 종료 #####");
	}

	@Test
	@Disabled
	void testUserModify() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testUserDelete() {
		fail("Not yet implemented");
	}

}

