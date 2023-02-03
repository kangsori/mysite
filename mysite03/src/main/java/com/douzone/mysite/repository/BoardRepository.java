package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> findAllbyPageAndKeyword(String keyword, int page, int rows) {
		Map<String,Object> map = new HashMap<>();
		map.put("startOffset", (page-1)*rows);
		map.put("keyword", keyword);
		map.put("rows", rows);
		List<BoardVo> list =  sqlSession.selectList("board.findAllbyPageAndKeyword",map);

		return list;
	}

	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.getTotalCount",keyword);
	}
	
	public void addContent(BoardVo vo) {
		sqlSession.insert("addContent",vo);
	}
	
	public BoardVo getView(Long no) {
		return sqlSession.selectOne("board.getView",no);
	}
	
	
	public BoardVo modifyContentView(Long no) {
		return sqlSession.selectOne("board.getView",no);
	}
	
	public void doModify(BoardVo vo) {
		sqlSession.update("doModify",vo);
		
	}
	
	public void doDelete(Long no, Long userNo) {
		Map<String,Long> map = new HashMap<>();
		map.put("no", no);
		map.put("userNo", userNo);
		
		sqlSession.delete("doDelete",map);
	}

	

}
