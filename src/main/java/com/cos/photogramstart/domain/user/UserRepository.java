package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// 어노테이션이 없어도 lOC등록 이 자동으로 됨 , JpaRepository 상속
public interface UserRepository  extends JpaRepository<User, Integer>{  // < 받을오프젝트 , pk 타입>
	
	User findByUsername(String username);

}
