package com.example.discussion_board.controller.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.discussion_board.dto.GidaiRequest;
import com.example.discussion_board.dto.GidaiResponse;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.service.GidaiService;
import com.example.discussion_board.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gidai")
@RequiredArgsConstructor
public class GidaiApiController {
	
	private final GidaiService gidaiService;
	private final UserService userService;
	
	@GetMapping
	public ResponseEntity<List<GidaiResponse>> getAll() {
		List<GidaiResponse> gidaiList = gidaiService.findAllGidai();
		return ResponseEntity.ok(gidaiList);
	}
	
	@PostMapping
	public ResponseEntity<GidaiResponse> createGidai(@RequestBody GidaiRequest request) {
		User user = userService.getUserEntityById(request.getUserId());
		GidaiResponse createdGidai = gidaiService.createGidai(request, user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{id}")
				.buildAndExpand(createdGidai.getId())
				.toUri();
		return ResponseEntity.created(location).body(createdGidai);
	}
	
}
