package com.winter.app.member;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class MemberDAOTest {
	
	@Autowired
	private MemberDAO memberDAO;

	@Test
	void test() throws Exception {
		MemberVO memberVO = new MemberVO();
		memberVO.setUsername("admin");
		
		memberVO = memberDAO.getMember(memberVO);
		
		log.info("Member : {}", memberVO);
		
		assertNotNull(memberVO);
	}

}
