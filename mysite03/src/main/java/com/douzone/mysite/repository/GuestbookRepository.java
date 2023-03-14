package com.douzone.mysite.repository;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() {
		List<GuestbookVo> list = sqlSession.selectList("guestbook.findAll");
		return list;
	}
	
	public List<GuestbookVo> findAll(Long startNo) {
		List<GuestbookVo> list = sqlSession.selectList("guestbook.findAllBystartNo",startNo);
		return list;
	}

	public void Insert(GuestbookVo vo) {
		sqlSession.insert("Insert",vo);
	}
	
	public void Delete(GuestbookVo vo) {
		sqlSession.delete("Delete",vo);
	}

	
}
