package com.example.discussion_board.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.discussion_board.model.Genre;

@Controller
@RequestMapping("/view/gidai")
public class GidaiViewController {

	@GetMapping("list")
	public String viewList() {
		return "gidai/list";
	}
	
	@GetMapping("registration")
	public String viewRegistration(Model model) {
		model.addAttribute("genres", Genre.values());
		return "gidai/registration";
	}

}
