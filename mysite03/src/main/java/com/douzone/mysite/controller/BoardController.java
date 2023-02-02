package com.douzone.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.BoardService;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/list")
	public String list(String kwd,String page,String rows,Model model){
		int setPage = page == null ? 1 : Integer.parseInt(page);
		int setRows = rows == null ? 5 : Integer.parseInt(rows);
		
		Map<String,Object> map = boardService.getContentsList(kwd,setPage,setRows);
		
		//패킹한걸 푸는것
		model.addAllAttributes(map);
		
		return "board/list";
	}
}
