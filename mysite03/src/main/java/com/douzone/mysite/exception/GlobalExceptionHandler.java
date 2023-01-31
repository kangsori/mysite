package com.douzone.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handlerException(Model model,Exception e) {
		//1. 로깅(Logging)
		StringWriter error = new StringWriter();
		e.printStackTrace(new PrintWriter(error));
		System.out.println(error.toString());
		
		//2. 사과페이지( 3.정상종료)
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception",error.toString());
		mav.setViewName("error/exception");
		
		return mav;
	}
}
