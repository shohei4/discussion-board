package com.example.discussion_board.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("view/discussion")
public class DiscussionViewController {
	@GetMapping("list")
	public String viewList() {
		return "/discussion/list";
	}
}
