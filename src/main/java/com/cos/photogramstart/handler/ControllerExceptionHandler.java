package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice   
public class ControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationException.class)
	public CMRespDto<?> validationException(CustomValidationException e) {
		System.out.print("ControllerException 메소드 탐");
		return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap());
		
		// 클라이언트를 위한 자바스크립
//		return Script.back(e.getErrorMap().toString());
	}

}
