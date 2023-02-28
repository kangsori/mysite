package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public void insert(UserVo vo) {
		sqlSession.insert("user.insert", vo);
	}

//	public UserVo findByEmailAndPassword(UserVo vo) {
//		return sqlSession.selectOne("user.findByEmailAndPassword",vo);
//	}
	
	public UserVo findByEmailAndPassword(String email,String password) {
		Map<String,Object> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		return sqlSession.selectOne("user.findByEmailAndPassword",map);
	}

	public UserVo findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo",no);
	}

	public void update(UserVo vo) {
		sqlSession.update("update",vo);
	}

	public UserVo findByEmail(String email) {
		return sqlSession.selectOne("user.findByEmail",email);
	}
	
}