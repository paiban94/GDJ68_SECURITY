package com.winter.app.member;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO {
	
	private String username;
	private String password;
	private String passwordCheck;
	private String name;
	private String email;
	private Date birth;
	private Date joinDate;

}
