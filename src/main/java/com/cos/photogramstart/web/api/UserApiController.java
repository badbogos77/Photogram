package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationAPIException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.subscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService;
	private final SubscribeService subscribeService;
	
	@GetMapping("/api/user/{pageuserid}/subscribe")
	public ResponseEntity<?> subscribe(@PathVariable int pageuserid, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
		List<subscribeDto> result = subscribeService.구독리스트(principalUserDetails.getUser().getId(), pageuserid);
		return new ResponseEntity<>(new  CMRespDto<>(1, "구독리스으 가져오기 완료",result), HttpStatus.OK)  ;
	}
	
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
			return new CMRespDto<>(1, "회원수정완료", userEntity) ;	// 응답시 userEntity의 모든 getter함수가 호출되고 JSON으로 파싱하여 응답한다.		
		}
	}
}
