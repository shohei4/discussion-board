package com.example.discussion_board.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("gidai")
public class GidaiViewController {

	@GetMapping("list")
	public String viewList() {
		return "gidai/list";
	}
	
	@GetMapping("registration")
	public String viewRegistration() {
		return "gidai/registration";
	}

}
