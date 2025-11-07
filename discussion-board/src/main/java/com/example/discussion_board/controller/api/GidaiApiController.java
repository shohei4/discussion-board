package com.example.discussion_board.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.dto.GidaiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gidai")
@RequiredArgsConstructor
public class GidaiApiController {
	
	
	
	@GetMapping
	public ResponseEntity<List<GidaiResponse>> getAll() {
		
	}
	
}
