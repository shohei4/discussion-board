package com.example.discussion_board.domain.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.domain.dto.UserResponse;
import com.example.discussion_board.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> getAll() {
		return ResponseEntity.ok(userService.findAllUser());
		
	}
}
