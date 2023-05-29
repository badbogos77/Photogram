package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	
	@Query(value="SELECT * FROM image WHERE USERID IN (SELECT TOUSERID from subscribe WHERE FROMUSERID = :principalId) ORDER BY ID DESC", nativeQuery=true)
	Page<Image> images(int principalId, Pageable pageable);
	
	@Query(value="SELECT A.* FROM image A, (SELECT IMAGEID, COUNT(*) LIKECNT FROM likes GROUP BY IMAGEID) B WHERE A.id = B.imageId ORDER BY LIKECNT DESC", nativeQuery = true)
	List<Image> mPopular();
}
