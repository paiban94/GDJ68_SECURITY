package com.winter.app.board.notice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.winter.app.board.BoardVO;
import com.winter.app.commons.Pager;

@SpringBootTest
class NoticeDAOTest {
	@Autowired
	private NoticeDAO noticeDAO;
	
	//@Test
	void addTest()throws Exception{
		for(int i=0;i<150;i++) {
			BoardVO boardVO = new BoardVO();
			boardVO.setBoardTitle("title"+i);
			boardVO.setBoardWriter("writer"+i);
			boardVO.setBoardContents("contents"+1);
			int result = noticeDAO.add(boardVO);
			if(i%10 == 0) {
				Thread.sleep(500);
			}
			
		}
		System.out.println("Finish");
		
	}
	
	@Test
	void getCountTest()throws Exception{
		Pager pager = new Pager();
		pager.setKind("1");
		pager.setSearch("20");
		Long count = noticeDAO.getCount(pager);
		assertEquals(2L, count);
		
	}

	@Test
	void getListTest()throws Exception {
		Pager pager = new Pager();
		pager.setStartRow(0L);
		pager.setLastRow(10L);
		pager.setKind("1");
		pager.setSearch("20");
		
		
		List<BoardVO> ar = noticeDAO.getList(pager);
		assertEquals(2, ar.size());
		
		
	}

}
