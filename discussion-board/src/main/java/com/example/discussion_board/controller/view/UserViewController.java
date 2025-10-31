package com.example.discussion_board.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.discussion_board.domain.service.UserService;
import com.example.discussion_board.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserViewController {
	
	private final UserService userService;
	private final UserMapper userMapper;
		
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
