package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.FileuploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private FileuploadService fileuploadService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> list =galleryService.getImages();
		model.addAttribute("list",list);
		
		return "gallery/index";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/upload")
	public String upload(
			@RequestParam("comments") String comments,
			@RequestParam("file") MultipartFile file) {
		//파일 업로드 
		String url = fileuploadService.restore(file);
		
		//DB insert할 GalleryVo 생성 / 값입력 
		GalleryVo vo = new GalleryVo();
		vo.setUrl(url);
		vo.setComments(comments);
		
		//DB insert처리 
		galleryService.addImage(vo);
		
		return "redirect:/gallery";
		
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/delete/{no}")
	public String delete(
			@AuthUser UserVo authUser,
			@PathVariable("no") Long no) {
		galleryService.removeImage(no);
		
		return "redirect:/gallery";
	}

}
