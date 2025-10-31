package com.example.discussion_board.controller.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.discussion_board.domain.service.UserService;
import com.example.discussion_board.dto.UserRequest;
import com.example.discussion_board.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> getAll() {
		List<UserResponse> users = userService.findAllUser();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponse> getByIdUser(@PathVariable Long id) {
		UserResponse user = userService.findByIdUser(id);
		return ResponseEntity.ok(user);
	}

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
		UserResponse createdUser = userService.createUser(request);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdUser.getId())
				.toUri();

		return ResponseEntity.created(location).body(createdUser);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<UserResponse> editUser(@PathVariable Long id, @RequestBody UserRequest request) {
		UserResponse editUser = userService.editUser(request);		
		return ResponseEntity.ok(editUser);
	}
	
}
