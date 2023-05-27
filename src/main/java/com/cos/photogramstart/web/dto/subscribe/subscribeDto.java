package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class subscribeDto {
	private int id;
	private String username;
	private String profileImageUrl;
	private int subscribeState;
	private int equalUserState;
}
