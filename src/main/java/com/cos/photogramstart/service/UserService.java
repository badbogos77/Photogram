package com.cos.photogramstart.service;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomAPIException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationAPIException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	@Value("${file.path}")
	private String uploadFolder;

	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;	
	
	@Transactional
	public User 프로필사진변경(int principalId, MultipartFile file) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid+"_" + file.getOriginalFilename();
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		try
		{
			Files.write(imageFilePath, file.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalId).orElseThrow(()-> {
			throw new CustomAPIException("해당 프로필이 존재하지 않습니다.");
		});
		
		userEntity.setProfileImageUrl(imageFileName);
		return userEntity;
	}
	
	@Transactional(readOnly = true)
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
