package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationAPIException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService;
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@PathVariable int id, 
														 @Valid UserUpdateDto userUpdateDto, 
														 BindingResult bindingResult,
														 @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
	
		if (bindingResult.hasErrors()) {	
			Map<String, String> errors = new HashMap<>();
			for(FieldError error: bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}		
			throw new CustomValidationAPIException("회원수정실패", errors);
			
		} else {

			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			principalUserDetails.setUser(userEntity);
			return new CMRespDto<>(1, "회원수정완료", userEntity) ;			
		}
	}
}
