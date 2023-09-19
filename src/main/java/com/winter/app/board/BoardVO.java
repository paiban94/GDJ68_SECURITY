package com.winter.app.board;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class BoardVO {
	private Long boardNo;
	private String boardTitle;
	private String boardWriter;
	private String boardContents;
	private Date boardDate;
	private Long boardHit;

}
