package com.cos.photogramstart.service;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationAPIException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;	
	
	public UserProfileDto 회원프로필(int pageUserid, int principalId) {
		UserProfileDto dto = new UserProfileDto();
		
		User userEntity = userRepository.findById(pageUserid).orElseGet(()-> {
			throw new CustomException("해당 프로필 페이지가 존재하지 않습니다");
		});
		
		dto.setUser(userEntity);
		dto.setImageCount(userEntity.getImages().size());
		dto.setPageOwnerState(pageUserid == principalId);
		
		boolean subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserid) > 0 ? true : false;
		dto.setSubScribeState(subscribeState);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserid);
		dto.setSubScribeCount(subscribeCount);
//		System.out.println("dto =======>" + dto);
		return dto;				
	}
	
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
