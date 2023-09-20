package com.winter.app.board.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.winter.app.board.BoardService;
import com.winter.app.board.BoardVO;
import com.winter.app.commons.FileManger;
import com.winter.app.commons.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeService implements BoardService {
	
	@Autowired
	private NoticeDAO noticeDAO;
	@Autowired
	private FileManger fileManger;
	
	//spEl
	//properties 값을 java 사용
	//@Value("${properties의 키}")
	@Value("${app.upload}")
	private String uploadPath;
	
	@Value("${app.board.notice}")
	private String boardName;


	@Override
	public List<BoardVO> getList(Pager pager) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.getList(pager);
	}

	@Override
	public int add(BoardVO boardVO, MultipartFile[] files) throws Exception {
		
		log.info("BoardNo : {}", boardVO.getBoardNo());
		int result= noticeDAO.add(boardVO);
		log.info("=====================");
		log.info("BoardNo : {}", boardVO.getBoardNo());
		
		
		for(MultipartFile multipartFile:files) {
			
			if(multipartFile.isEmpty()) {
				continue;
			}
			
			NoticeFileVO fileVO = new NoticeFileVO();
			String fileName=fileManger.save(this.uploadPath+this.boardName, multipartFile);
			fileVO.setBoardNo(boardVO.getBoardNo());
			fileVO.setFileName(fileName);
			fileVO.setOriName(multipartFile.getOriginalFilename());
			result = noticeDAO.fileAdd(fileVO);
		}
		
		return result;//;
	}

	@Override
	public BoardVO getDetail(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.getDetail(boardVO);
	}

	@Override
	public int setUpdate(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.setUpdate(boardVO);
	}

	@Override
	public int setDelete(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return noticeDAO.setDelete(boardVO);
	}

}
