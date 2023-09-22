package com.winter.app.member;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member/*")
@Slf4j
public class MemberController {
	
	//service
	@Autowired
	private MemberService memberService;
	
//	@GetMapping("join")
//	public void setJoin(Model model)throws Exception{
//		MemberVO memberVO = new MemberVO();
//		model.addAttribute("memberVO", memberVO);
//	}
	@GetMapping("join")
	public void setJoin(@ModelAttribute MemberVO memberVO)throws Exception{
		
	}
	
	@PostMapping("join")
	public String setJoin(@Valid MemberVO memberVO,BindingResult bindingResult, MultipartFile photo)throws Exception{
		
		 boolean check = memberService.getMemberError(memberVO, bindingResult);
		
		
		if(bindingResult.hasErrors() || check) {
			return "member/join"; 
		}
		
		log.info("Photo : {} --- size : {}", photo.getOriginalFilename(), photo.getSize());
		return "redirect:../";
	}
	

}
