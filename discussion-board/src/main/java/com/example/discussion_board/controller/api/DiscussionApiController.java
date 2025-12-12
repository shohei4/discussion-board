package com.example.discussion_board.controller.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.dto.DiscussionItemRequest;
import com.example.discussion_board.dto.DiscussionItemResponse;
import com.example.discussion_board.security.CustomUserDetails;
import com.example.discussion_board.service.DiscussionItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discussion")
public class DiscussionApiController {
	
	private final DiscussionItemService discussionItemService;
	
	@GetMapping("/list/{gidaiId}")
	public ResponseEntity<List<DiscussionItemResponse>> getAll(@PathVariable Long gidaiId) {
		List<DiscussionItemResponse> responses = discussionItemService.findAllByGidaiId(gidaiId);
		return ResponseEntity.ok(responses);
	}
	
	@PostMapping("/save/{gidaiId}")
	public ResponseEntity<DiscussionItemResponse> save(@PathVariable Long gidaiId, 
			@RequestBody DiscussionItemRequest request, @AuthenticationPrincipal CustomUserDetails user){
		DiscussionItemResponse response = discussionItemService.createDiscussionItem(request, gidaiId, user.getId());
		
		URI location = URI.create("/api/discussoin/" + response.getId());
		return ResponseEntity.created(location).body(response);
	}
}
