package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utils.MvcUtil;

public class ModifyAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long no = Long.parseLong(request.getParameter("no"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo vo= new BoardVo();
		vo.setNo(no);
		vo.setTitle(title);
		vo.setContents(content);
		
		new BoardDao().Modify(vo);
		

		BoardVo boardVo = new BoardDao().View(no);
		request.setAttribute("boardVo", boardVo);
		
		MvcUtil.forward("board/view.jsp", request, response);

	}

}
