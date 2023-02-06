package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/list")
	public String list(@RequestParam(value="kwd", required=true, defaultValue="") String kwd, 
					   @RequestParam(value="page", required=true, defaultValue="1") Integer page, 
					   @RequestParam(value="rows", required=true, defaultValue="5") Integer rows, 	
			           Model model) {
		Map<String, Object> map = boardService.getContentsList(kwd, page, rows);

		// 패킹한걸 푸는것
		model.addAllAttributes(map);

		return "board/list";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(@RequestParam(value="no", required = true, defaultValue ="0") Integer no, 
			            Model model) {
		model.addAttribute("no", no);

		return "/board/write";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(BoardVo vo) {
		boardService.addContents(vo);

		return "redirect:/board/list";
	}

	@RequestMapping(value = "/view")
	public String view(Long no,String kwd, String page, String rows, Model model
			           ,HttpServletRequest request
			           ,HttpServletResponse response) {
		String cookieValue = "";
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
            boardService.updateHit(no);
		}
		BoardVo vo = boardService.getContents(no);
		model.addAttribute("boardVo", vo);
		model.addAttribute("kwd",kwd);
		model.addAttribute("page",page);
		model.addAttribute("rows",rows);

		return "/board/view";
	}
	
	@Auth
	@RequestMapping(value="/delete")
	public String delete(@AuthUser UserVo authUser,Long no) {
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board/list";
	}
	
	@Auth
	@RequestMapping(value="/modify", method = RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser, Long no,String kwd, String page, String rows, Model model) {

		model.addAttribute("kwd",kwd);
		model.addAttribute("page",page);
		model.addAttribute("rows",rows);
		
		BoardVo boardVo =boardService.getContents(no,authUser.getNo());
		model.addAttribute("boardVo",boardVo);
		
		return "/board/modify";
	}
	
	@Auth
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
