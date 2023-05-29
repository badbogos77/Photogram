package com.cos.photogramstart.web.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

	private final ImageService imageService;
	private final LikesService likesService;
	
	@GetMapping("/api/images")
	public ResponseEntity<?> images(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
			@PageableDefault(size=3) Pageable pageable ) {
		Page<Image> images = imageService.이미지히스토리(principalUserDetails.getUser().getId(),  pageable);
		return  new ResponseEntity<>(new CMRespDto<>(1, "이미지 히스토리 가져오기", images), HttpStatus.OK)  ;
	}
	  
	@PostMapping("/api/images/{imageId}/likes")
	public ResponseEntity<?> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
		likesService.좋아요(imageId, principalUserDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 완료", null), HttpStatus.OK);
	}
	
	@DeleteMapping("/api/images/{imageId}/likes")
	public ResponseEntity<?> unlikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
		likesService.좋아요취소(imageId, principalUserDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요취소 완료", null), HttpStatus.OK);
	}
}
