package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utils.MvcUtil;

public class ViewAndModifyAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String mode=request.getParameter("a");
		
		Long boardNo = Long.parseLong(request.getParameter("no"));
		
		BoardVo vo = new BoardDao().View(boardNo);
		request.setAttribute("boardVo", vo);
		
		if("view".equals(mode)) {
			new BoardDao().ViewCount(boardNo);
			MvcUtil.forward("board/view.jsp", request, response);
		}else {
			MvcUtil.forward("board/modify.jsp", request, response);
		}
		
	}

}
