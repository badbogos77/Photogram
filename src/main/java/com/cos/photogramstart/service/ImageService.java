package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	@Value("${file.path}")
	private String uploadFolder;
	
	private final ImageRepository imageRepository;
	
	@Transactional(readOnly=true)
	public List<Image> 인기사진보기() {
		List<Image> popular = imageRepository.mPopular();
		
		return popular;
	}
	
	@Transactional(readOnly=true)
	public Page<Image> 이미지히스토리(int principalId, Pageable pageable) {
	
	  Page<Image> images = imageRepository.images(principalId, pageable);
	  
	  // 좋아요 도 가지고 가야함
	  images.forEach((image)-> {
		  image.getLikes().forEach((like)-> {
			  
			  image.setLikesCount(image.getLikes().size());
			  
			  if (like.getUser().getId() == principalId) {
				  image.setLikesState(true);
			  }
		  }); 
	  });
		return images;
	}
	
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalUserDetails principalUserDetails) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid+"_" + imageUploadDto.getFile().getOriginalFilename();
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		try
		{
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Image image = imageUploadDto.toEntity(imageFileName, principalUserDetails.getUser());
		imageRepository.save(image);
		
	}
}
