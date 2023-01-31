package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getMessageList(){
		List<GuestbookVo> list=guestbookRepository.findAll();
		return list;
	}
	
	public void deleteMessage(GuestbookVo vo) {
		guestbookRepository.Delete(vo);
	}
	
	public void addMessage(GuestbookVo vo) {
		guestbookRepository.Insert(vo);
		
	}
}
