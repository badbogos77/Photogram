package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationAPIException extends RuntimeException {

	/**
	 *  객체구분시 사용 : 개발에 큰 의미 없음
	 */
	private static final long serialVersionUID = 1L;	

	private Map<String, String> errorMap;
	
	public CustomValidationAPIException(String message) {
		super(message);
	}
	
	public CustomValidationAPIException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}
}
