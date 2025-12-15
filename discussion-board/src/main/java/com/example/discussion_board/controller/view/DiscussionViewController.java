package com.example.discussion_board.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/discussion")
public class DiscussionViewController {
	@GetMapping("/list/{gidaiId}")
	public String viewList(@PathVariable Long gidaiId, Model model) {
		model.addAttribute("gidaiId", gidaiId);
		return "discussion/list";
	}
	
	@GetMapping("/registration")
	public String viewRegistration() {
		return "registration";
	}
}
