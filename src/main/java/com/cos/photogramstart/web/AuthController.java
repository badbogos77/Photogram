package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthController {	
	
	private final AuthService authService;
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	@GetMapping("/auth/signup")
	public String signupPage() {
		return "auth/signup";
	}
	
	@GetMapping("/auth/signin")
	public String signinPage() {
		return "auth/signin";
	}

	@PostMapping("/auth/singup")
	public String singup(@Valid SignupDto signupdto, BindingResult bindingResult) {
		
		Map<String, String> errors = new HashMap<>();
		if (bindingResult.hasErrors()) {
			for(FieldError error: bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());						
			}			
			throw new CustomValidationException("유효성 검사 실패", errors);
		} else {
			log.info(signupdto.toString());
			User user = signupdto.toEntity();		
			User result =  authService.회원가입(user);
			return "/auth/signin";	
		}			
	}
}
