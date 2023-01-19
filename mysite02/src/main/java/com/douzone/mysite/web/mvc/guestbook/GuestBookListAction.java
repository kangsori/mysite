package com.douzone.mysite.web.mvc.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestbookDao;
import com.douzone.mysite.vo.GuestbookVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utils.MvcUtil;

public class GuestBookListAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<GuestbookVo> list = new GuestbookDao().findAll(); 
		
		request.setAttribute("list", list);
		MvcUtil.forward("guestbook/list.jsp", request, response);
	}

}
