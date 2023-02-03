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
	
	private static final int PAGE_SIZE=5; //페이지 리스트의 페이지 수
	
	@Autowired
	private BoardRepository boardRepository;
	
	//게시물등록
	public void addContents(BoardVo vo) {
		boardRepository.addContent(vo);
	}
	
	//뷰
	public BoardVo getContents(Long no) {
		return boardRepository.getView(no);
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
	
	public Map<String,Object> getContentsList(String kwd, int page, int rows) {
		int totalCount = boardRepository.getTotalCount(kwd);
		
		List<BoardVo> list=boardRepository.findAllbyPageAndKeyword(kwd,page,rows);
		
		int totalPage = (int)Math.ceil((double)totalCount/(double)rows); 
		int beginPage = ((int)Math.ceil((double)page/(double)PAGE_SIZE)-1)*PAGE_SIZE+1; 
		int endPage = totalPage<(beginPage+PAGE_SIZE-1)? totalPage :(beginPage+PAGE_SIZE-1) ;
		int prevPage = (page-1); 
		int nextPage = (page+1)>totalPage? 0:(page+1); 
		int startNo = totalCount -((page-1)*rows)  ;
		
		//리스트 정보를 맵에 저장
		Map<String,Object> map = new HashMap<>();
		
		map.put("page", page);
		map.put("rows", rows);
		map.put("kwd", kwd);
		
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("beginPage", beginPage);
		map.put("endPage", endPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("startNo", startNo);
		
		return map;
	}
}
