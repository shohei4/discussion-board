package com.example.discussion_board.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.discussion_board.domain.mapper.UserMapper;
import com.example.discussion_board.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserViewController {
	
	private final UserService userService;
	private final UserMapper userMapper;
		
	@GetMapping 
	public String showUsers() {
		return "user/list";		
	}
	
	@GetMapping
	public String showEditUser() {
		return "user/edit";
	}
	

}
