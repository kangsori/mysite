package com.douzone.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.BoardService;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@RequestMapping("/")
	public String index(Model model) {
		Map<String,Object> map = boardService.getContentsList(1, "");
		
		//패킹한걸 푸는것
		model.addAllAttributes(map);
		return "board/index";
	}
}
