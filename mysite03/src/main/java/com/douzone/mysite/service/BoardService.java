package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	private static final int LIST_SIZE=5; //리스팅되는 게시물의 수
	private static final int PAGE_SIZE=5; //페이지 리스트의 페이지 수
	
	@Autowired
	private BoardRepository boardRepository;
	
	//게시물등록
	public void addContents(BoardVo vo) {
		
	}
	
	//뷰
	public BoardVo getContents(Long no) {
		return null;
	}
	
	//수정모드 (userno은 보안으로 추가된것)
	public BoardVo getContents(Long no, Long userNo) {
		//액세스 제어 필요함
		return null;
	}
	
	//업데이트
	public void updateContents(BoardVo vo) {
		
	}
	
	//삭제
	public void deleteContents(Long no, Long userNo) {
		
	}
	
	public Map<String,Object> getContentsList(int page, String keyword) {
		int totalCount = boardRepository.getTotalCount(keyword);
		List<BoardVo> list=boardRepository.findAllbyPageAndKeyword(page,keyword,LIST_SIZE);
		
		int baginPage = 0;
		int prevPage =0;
		int nextPage =0;
		int endPage =0;
		
		//리스트 정보를 맵에 저장
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("baginPage", baginPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("endPage", endPage);
		
		return map;
	}
}
