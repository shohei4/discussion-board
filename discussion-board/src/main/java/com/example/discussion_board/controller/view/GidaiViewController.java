package com.example.discussion_board.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.discussion_board.model.Genre;
import com.example.discussion_board.service.GidaiService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/gidai")
public class GidaiViewController {
	
	private final GidaiService service;
	
	@GetMapping("/list")
	public String viewList() {
		return "gidai/list";
	}
	
	@GetMapping("/registration")
	public String viewRegistration(Model model) {
		model.addAttribute("genres", Genre.values());
		return "gidai/registration";
	}
	
	@GetMapping("/detail/{id}")
	public String viewDetail(@PathVariable Long id, Model model) {
		model.addAttribute("gidaiId", id);
		return "gidai/detail";
	}
	
	@GetMapping("/update/{id}")
	public String viewUpdate(@PathVariable Long id, Model model) {
		model.addAttribute("gidaiId",id);
		return "gidai/update";
	}

}
