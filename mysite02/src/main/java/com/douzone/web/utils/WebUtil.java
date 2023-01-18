package com.douzone.web.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {
	public static void forward(String viewName,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException  {
		request
		.getRequestDispatcher("/WEB-INF/views/"+viewName)
		.forward(request, response);
	}
}
