package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 20, unique = true) 
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)	
	private String email;
	
	private String website;
	private String bio;	
	private String phone;
	private String gender;
	private String role;
	
	private String profileImageUrl;
	
	// 나는 연관관계의 주인이 아니다. 그러므로 테이블에 칼럼을 만들지마.
		// User를 Select할 때 해당 User id로 등록된 image들을 다 가져와
		// Lazy = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마 - 대신 getImages() 함수의 image들이 호출될 때 가져와!!
		// Eager = User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와!!
	@JsonIgnoreProperties({"user"})
	@OneToMany(mappedBy = "user", fetch=FetchType.LAZY)
	private List<Image> images;   // 양방향 매핑
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 insert 되기 직전에 실행됨
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
