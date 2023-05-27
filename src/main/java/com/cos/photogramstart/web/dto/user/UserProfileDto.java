package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProfileDto {
	
	private boolean pageOwnerState;
	private User user;
	private int imageCount;
	private boolean subScribeState;
	private int subScribeCount;

}
