package com.winter.app.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
	
	public MemberVO getMember(MemberVO memberVO)throws Exception;

}
