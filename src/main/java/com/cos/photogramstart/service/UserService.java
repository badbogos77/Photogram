package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomValidationAPIException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public User 회원수정(int id, User user) {
		// 1. 영속화 : .get() 무조건 찾음 / orElseThrow()  못찾으면 exception 발생
		User userEntity = userRepository.findById(id).orElseThrow(() -> { return new CustomValidationAPIException("찾을수 없는 id입니다.");});		
		
		// 2. 영속화된 오브젝트 수정 - 더티체킹
		userEntity.setName(user.getName());
		// 비밀번호 암호화
		String rawpwd = user.getPassword();
		String endpwd = bCryptPasswordEncoder.encode(rawpwd);		
		userEntity.setPassword(endpwd);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
		return userEntity;
	}
}
