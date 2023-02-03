package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/list")
	public String list(String kwd, String page, String rows, Model model) {
		int setPage = page == null ? 1 : Integer.parseInt(page);
		int setRows = rows == null ? 5 : Integer.parseInt(rows);

		Map<String, Object> map = boardService.getContentsList(kwd, setPage, setRows);

		// 패킹한걸 푸는것
		model.addAllAttributes(map);

		return "board/list";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(Long no, Model model) {
		model.addAttribute("no", no);

		return "/board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(BoardVo vo) {
		boardService.addContents(vo);

		return "redirect:/board/list";
	}

	@RequestMapping(value = "/view")
	public String view(Long no,String kwd, String page, String rows, Model model) {
		BoardVo vo = boardService.getContents(no);

		model.addAttribute("boardVo", vo);
		model.addAttribute("kwd",kwd);
		model.addAttribute("page",page);
		model.addAttribute("rows",rows);

		return "/board/view";
	}
	
	@RequestMapping(value="/delete")
	public String delete(Long no,Long userNo) {
		boardService.deleteContents(no, userNo);
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.GET)
	public String modify(Long no,String kwd, String page, String rows,HttpSession session, Model model) {
		// Access Control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		model.addAttribute("kwd",kwd);
		model.addAttribute("page",page);
		model.addAttribute("rows",rows);
		
		if(authUser == null) {
			model.addAttribute("no",no);
			return "/board/view";
		}
		//////////////////////////////////////////////////
		BoardVo boardVo =boardService.getContents(no,authUser.getNo());
		model.addAttribute("boardVo",boardVo);
		
		return "/board/modify";
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String modify(BoardVo vo,String kwd, String page, String rows,Model model) {
		boardService.updateContents(vo);
		
		model.addAttribute("no",vo.getNo());
		model.addAttribute("kwd",kwd);
		model.addAttribute("page",page);
		model.addAttribute("rows",rows);

		return "redirect:/board/view";
	}
}
