package com.cos.photogramstart.handler.ex;

public class CustomException extends RuntimeException {

	/**
	 *  객체구분시 사용 : 개발에 큰 의미 없음
	 */
	private static final long serialVersionUID = 1L;	

	
	public CustomException(String message) {
		super(message);
	}

}
