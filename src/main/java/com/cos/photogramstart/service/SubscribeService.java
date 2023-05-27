package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomAPIException;
import com.cos.photogramstart.web.dto.subscribe.subscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em;
	
	@Transactional(readOnly = true)
	public List<subscribeDto> 구독리스트(int principalId, int pageUserId) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT B.id, B.username, B.profileImageUrl, ");
		sb.append("if ((SELECT 1 FROM subscribe WHERE FROMUSERID= ? AND TOUSERID=B.ID), 1, 0) subscribeStatue, ");
		sb.append("if ((b.id = ?),1,0) equalUserState ");
		sb.append("FROM subscribe A ");
		sb.append("INNER JOIN user B ");
		sb.append("ON A.toUserid = B.id ");
		sb.append("WHERE A.fromUserId=? ");
		// 쿼리작성
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		//쿼리실행 qlrm
		JpaResultMapper result = new JpaResultMapper();
		List<subscribeDto> subscribeDtos = result.list(query, subscribeDto.class);
		return subscribeDtos;
	}
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomAPIException("이미 구독을 하였습니다");
		}
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);		
	}

}
