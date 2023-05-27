package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;
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
public class Image {
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String caption;
	
	private String postImageUrl;
	
	@JsonIgnoreProperties("{user}")
	@JoinColumn(name="userId")
	@ManyToOne(fetch= FetchType.EAGER)
	private User user;

	private LocalDateTime createDate;
	
	@PrePersist // 디비에 insert 되기 직전에 실행됨
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
