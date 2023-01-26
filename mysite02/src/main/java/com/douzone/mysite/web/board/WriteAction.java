package com.douzone.mysite.web.board;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utils.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = request.getParameter("no")==""? 0: Long.parseLong(request.getParameter("no"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String userNo = request.getParameter("userNo");

		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserNo(Long.parseLong(userNo));
		
		try {
			new BoardDao().Insert(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		MvcUtil.redirect(request.getContextPath()+"/board", request, response);
		
	}

}
