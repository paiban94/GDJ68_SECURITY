package com.winter.app.board.notice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.winter.app.board.BoardVO;

@SpringBootTest
class NoticeDAOTest {
	@Autowired
	private NoticeDAO noticeDAO;
	
	@Test
	void addTest()throws Exception{
		BoardVO boardVO = new BoardVO();
		boardVO.setBoardTitle("title");
		boardVO.setBoardWriter("writer");
		boardVO.setBoardContents("contents");
		int result = noticeDAO.add(boardVO);
		assertEquals(1, result);
	}

	@Test
	void getListTest()throws Exception {
		List<BoardVO> ar = noticeDAO.getList();
		assertNotEquals(0, ar.size());
		
		
	}

}
