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
	
	private static final int PAGE_COUNT=5;

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		int rows = request.getParameter("rows") == null ? 5 : Integer.parseInt(request.getParameter("rows"));;
		String kwd = request.getParameter("kwd")== null ? "" : request.getParameter("kwd");
		
		List<BoardVo> list = new BoardDao().findAll(page,rows,kwd); 
		int totalCount = new BoardDao().findPaging(kwd); //38개 
		int totalPage = (int)Math.ceil((double)totalCount/(double)rows); //8페이지 
		int beginPage = ((int)Math.ceil((double)page/(double)PAGE_COUNT)-1)*PAGE_COUNT+1; //6페이지 
		int endPage = totalPage<(beginPage+PAGE_COUNT-1)? totalPage :(beginPage+PAGE_COUNT-1) ;//8페이지 
		int prevPage = (page-1); 
		int nextPage = (page+1)>totalPage? 0:(page+1); 
	
		request.setAttribute("page", page);
		request.setAttribute("rows", rows);
		request.setAttribute("kwd", kwd) ;
		
		request.setAttribute("list", list);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("prevPage", prevPage);
		request.setAttribute("nextPage", nextPage);
		
		/*
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("baginPage", baginPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("endPage", endPage);
		*/
		MvcUtil.forward("board/list.jsp", request, response);

	}

}
