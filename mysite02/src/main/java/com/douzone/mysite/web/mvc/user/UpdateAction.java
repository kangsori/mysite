package com.douzone.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.utils.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Access Control (접근제어) ///////////////////////////////////////////////
		/*HttpSession session = request.getSession();
		if (session == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}*/
		//////////////////////////////////////////////////////////////////////////

		Long no = Long.parseLong(request.getParameter("no"));
		String name =request.getParameter("name");
		String email =request.getParameter("email");
		String password =request.getParameter("password");
		String gender =request.getParameter("gender");
		
		UserVo vo = new UserVo();
		vo.setNo(no);
		vo.setName(name);
		vo.setEmail(email);
		vo.setPassword(password);
		vo.setGender(gender);
		
		new UserDao().Update(vo);
		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", vo);
		
		MvcUtil.redirect(request.getContextPath()+"/user?a=updateform", request, response);

	}

}
