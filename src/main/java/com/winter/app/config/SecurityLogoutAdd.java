package com.winter.app.config;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.winter.app.board.PostVO;
import com.winter.app.member.MemberVO;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class SecurityLogoutAdd implements LogoutHandler{
	
	@Value("{spring.security.oauth2.client.registration.kakao.client-secret=8773916d11494d8f13b93acb5918f548}")
	public String adminKey;
	
	
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  {
		
		log.info("====== Logout Add =============");
		
		//this.logoutForKakao2(response);
		this.userWebClient();
	}

	//web-client
	private void userWebClient() {
		WebClient webClient = WebClient.builder()
							.baseUrl("https://jsonplaceholder.typicode.com/")
							.build();
		Mono<ResponseEntity<PostVO>> res = webClient.get()		
				 .uri("posts/1")
				 .retrieve()
				 .toEntity(PostVO.class);
		
		PostVO postVO = res.block().getBody();
	
		log.info("++++ WebClient {} ", postVO);
	}
	
	//카카오계정과 함께 로그아웃. resttemplate을 이용해서 해보기
	private void logoutForKakao2 (HttpServletResponse response) {
		//RestTemplate restTemplate = new RestTemplate();
		StringBuffer sb = new StringBuffer();
		sb.append("https://kapi.kakao.com/v1/user/logout?");
		sb.append("client_id=");
		sb.append("5ee6d9390b55dd37968b753e7934f31d");
		sb.append("&logout_redirect_uri=");
		sb.append("http://localhost:82/member/kakaoLogout");
		
		//ResponseEntity<String> res =restTemplate.getForEntity(sb.toString(),String.class);
	
		//log.info("+++ 카카오계정과 함께 로그 아웃 : {}",res.getBody());
		try {
			response.sendRedirect(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	//카카오 로그아웃
public void logoutForKakao(Authentication authentication) {
	RestTemplate restTemplate = new RestTemplate();
	MemberVO memberVO = (MemberVO)authentication.getPrincipal();
	//log.info("=============AccessToken : {} ============", memberVO.getAccessToken());
	
	HttpHeaders headers = new HttpHeaders();
	//headers.add("Content-Type", "application/x-www-form-urlencoded");
	headers.add("Authorization", "KakaoAK 8773916d11494d8f13b93acb5918f548");
	
	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	params.add("target_id_type", "user_id");
	params.add("target_id", memberVO.getName());
	log.info("============LogOut ID : {} ",memberVO.getName());
	HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(params, headers);
	ResponseEntity<String> res = restTemplate.postForEntity("https://kapi.kakao.com/v1/user/logout", req, String.class);
	
	String result = res.getBody();
	
	log.info("============로그아웃 ID : {} ", result);

}


//연습용	
	public void useRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(); 	
		
		//fake json
		//parameter post일 경우
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("postId", "1");
		
		
		
		
		
		
		//선생님  RestTemplate 노션참고	
		HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(params,null);
		//결과가 하나 일 때.
		//ResponseEntity<PostVO> res = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/1", PostVO.class, req);
		//PostVO result = res.getBody();
		
		//결과가 여러개 일 때
		//List<PostVO> res = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", List.class, req);
		
		ResponseEntity<String> res = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/comments?PostId=1", String.class, req);
		log.info("****** Comment List : {} *******", res);
	}

}
