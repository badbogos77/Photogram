package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;

@Controller
public class UserController {

	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id ) {
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalUserDetails  principalUserDetails) {
		// 1. 첫번째 방법 (어노테이션을 사용해서 바로 가져오는방법)
//		System.out.println("1. 세션정보 : " + principalUserDetails.getUser());
		
		// 2.두번째 방법
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		PrincipalUserDetails mPrincipalUserDetails = (PrincipalUserDetails)auth.getPrincipal();
//		System.out.println("2. 세션정보 : " + mPrincipalUserDetails.getUser());
		
		// <sec:authorize access="isAuthenticated()"> 를 사용하면 안 넘겨도 됨
//		model.addAttribute("principal", principalUserDetails.getUser());
		return "user/update";
	}
}
