package com.example.discussion_board.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/view/users")
@RequiredArgsConstructor
public class UserViewController {
		
	@GetMapping("/list")
	public String showUsers() {
		return "user/list";		
	}
	
	@GetMapping("/registration")
	public String showRegistration(){
		return "user/registration";
	}
	
	@GetMapping("/edit")
	public String showEditUser() {
		return "user/edit";
	}
	

}
