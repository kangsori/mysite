package com.douzone.mysite.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {
	
	@RequestMapping({"","/"})
	public void index() {
	}
	
}