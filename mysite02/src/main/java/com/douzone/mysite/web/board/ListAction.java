package com.douzone.mysite.web.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utils.MvcUtil;

public class ListAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		int rows = request.getParameter("rows") == null ? 10 : Integer.parseInt(request.getParameter("rows"));;
		String kwd = request.getParameter("kwd")== null ? "" : request.getParameter("kwd");
	
		List<BoardVo> list = new BoardDao().findAll(page,rows,kwd); 
		int count = new BoardDao().findPaging(kwd); 
		int pageNo = (int)Math.ceil((double)count/(double)rows);
		int beginNo = ((page/5)*5)+1;
				
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("rows", rows);
		request.setAttribute("kwd", kwd);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("beginNo", beginNo);
		
		MvcUtil.forward("board/list.jsp", request, response);

	}

}
