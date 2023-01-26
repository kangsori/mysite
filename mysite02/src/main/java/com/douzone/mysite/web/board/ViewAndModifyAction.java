package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
		String cookieValue = "";
		if("view".equals(mode)) {
			
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if (cookie.getName().equals(request.getParameter("no"))) {
	                    cookieValue= cookie.getValue();
	                }
	            }
	        } 
			
			if(cookieValue.equals("")) {
				Cookie c = new Cookie(request.getParameter("no"),request.getParameter("no"));
	            c.setMaxAge(60 * 60 * 24);  /* 쿠키 시간 */
	            response.addCookie(c);
	            new BoardDao().ViewCount(boardNo);
			}
           
			MvcUtil.forward("board/view.jsp", request, response);
		}else {
			MvcUtil.forward("board/modify.jsp", request, response);
		}
		
	}

}
