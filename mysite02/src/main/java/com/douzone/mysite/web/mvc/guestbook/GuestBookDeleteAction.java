package com.douzone.mysite.web.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestbookDao;
import com.douzone.mysite.vo.GuestbookVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utils.MvcUtil;

public class GuestBookDeleteAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no =request.getParameter("no");
		String password =request.getParameter("password");
		
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(Long.parseLong(no));
		vo.setPassword(password);
		
		new GuestbookDao().Delete(vo);

		MvcUtil.redirect(request.getContextPath()+"/guestbook?a=list", request, response);

	}

}
