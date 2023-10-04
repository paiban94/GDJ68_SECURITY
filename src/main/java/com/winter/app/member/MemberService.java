package com.winter.app.member;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService extends DefaultOAuth2UserService implements UserDetailsService {
	
	//DAO
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//social login 사용
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("=====social Login 처리 진행=====");
		ClientRegistration clientRegistration = userRequest.getClientRegistration();
		log.info("============{}==============",clientRegistration);
		
		//사용자의 정보는 super.loadUser로 꺼내온다
		OAuth2User auth2User = super.loadUser(userRequest);
		
		log.info("============ auth2User :{}==============",auth2User);
		//social에 kakao 들어감
		String social = clientRegistration.getRegistrationId();
		if(social.equals("kakao")) {
			auth2User = this.forKakao(auth2User);
		}
		
		return auth2User;
	}
	
	private OAuth2User forKakao(OAuth2User auth2User) {
		MemberVO memberVO = new MemberVO();
		LinkedHashMap<String,String> map = auth2User.getAttribute("properties");
		//Object obj =auth2User.getAttribute("properties").getClass();
		//memberVO.setUsername();
		log.info("111******{}*****",auth2User.getAttribute("properties").toString());
	
		LinkedHashMap<String, Object> kakaoAccount = auth2User.getAttribute("kakao_account");
		LinkedHashMap<String, Object> profile = (LinkedHashMap<String, Object>)kakaoAccount.get("profile");
		
		log.info("NickName : {} ====", profile.get("nickname"));
		log.info("ProfileImage : {} ====", profile.get("profile_image_url"));
		log.info("Email : {} ====", kakaoAccount.get("email"));
		log.info("Birth : {} ====", kakaoAccount.get("birthday"));
		
		String birth = kakaoAccount.get("birthday").toString(); //생일
		//시작번호 이상 끝번호 미만
		String m = birth.substring(0,2);
		//시작인덱스만 넣으면 끝까지 감
		String d = birth.substring(2);
		//객체만들기, 연도만 빼기
		Calendar ca = Calendar.getInstance();
		int y = ca.get(Calendar.YEAR);
		//더하기를 전문으로 하는 클래스 : StringBuffer
		StringBuffer sb = new StringBuffer();
		sb.append(y);
		sb.append("-");
		sb.append(m);
		sb.append("-");
		sb.append(d);
		//sb.append(y).append("-") ~~~~~
		memberVO.setUsername(map.get("nickname"));
		memberVO.setName(map.get("nickname"));
		memberVO.setEmail(kakaoAccount.get("email").toString());
		memberVO.setBirth(Date.valueOf(sb.toString()));
		
		memberVO.setAttributes(auth2User.getAttributes());
		
		List<RoleVO> list = new ArrayList<>();
		RoleVO roleVO = new RoleVO();
		roleVO.setRoleName("ROLE_MEMBER");
		
		list.add(roleVO);
		
		memberVO.setRoleVOs(list);
		
		//sb 는 버퍼타입이라 문자열이 들어가야 함으로 toString을 붙여준다
		log.info("Date : {}",Date.valueOf(sb.toString()));
		
		return memberVO;
	}
	
	//server login
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		log.info("====== 로그인 시도 중 ==========");
		MemberVO memberVO = new MemberVO();
		memberVO.setUsername(username);
		try {
			memberVO = memberDAO.getMember(memberVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			memberVO=null;
		}
		return memberVO;
	}
	

	//검증메서드
	public boolean getMemberError(MemberVO memberVO, BindingResult bindingResult) throws Exception {
		boolean check=false; //error가 없다, true : error가 있다. 검증실패
		
		//password 일치 검증
		if(!memberVO.getPassword().equals(memberVO.getPasswordCheck())) {
			check=true; //check=!check
			
			bindingResult.rejectValue("passwordCheck", "memberVO.password.equalCheck");
		}
		
		// ID 중복 검사
		MemberVO checkVO = memberDAO.getMember(memberVO);
		
		if(checkVO != null) {
			check = true;
			bindingResult.rejectValue("username", "memberVO.username.equalCheck");
		}
		
		
		return check;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int setJoin (MemberVO memberVO)throws Exception{
		memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));
		int result=memberDAO.setJoin(memberVO);
		Map<String, Object> map=new HashMap<>();
		map.put("roleNum", 3);
		map.put("username", memberVO.getUsername());
		result = memberDAO.setMemberRole(map);		
		return result;
	}

}
