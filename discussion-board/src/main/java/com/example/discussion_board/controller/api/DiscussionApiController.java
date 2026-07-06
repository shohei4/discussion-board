package com.example.discussion_board.controller.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.dto.DiscussionItemRequest;
import com.example.discussion_board.dto.DiscussionItemResponse;
import com.example.discussion_board.dto.DiscussionItemWithReplyResponse;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.security.CustomUserDetails;
import com.example.discussion_board.service.DiscussionItemService;
import com.example.discussion_board.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discussion")
public class DiscussionApiController {
	
	private final DiscussionItemService discussionItemService;
	private final UserService userService;
	
	@GetMapping("/list/{gidaiId}")
	public ResponseEntity<List<DiscussionItemWithReplyResponse>> getAll(@PathVariable Long gidaiId) {
		List<DiscussionItemWithReplyResponse> responses = discussionItemService.findAllByGidaiId(gidaiId);
		return ResponseEntity.ok(responses);
	}
	
	@PostMapping("/save/{gidaiId}")
	public ResponseEntity<DiscussionItemResponse> save(@PathVariable Long gidaiId, 
			@RequestBody DiscussionItemRequest request, @AuthenticationPrincipal CustomUserDetails userDetails){
		
		User user = userService.getUserEntityById(userDetails.getId());
		DiscussionItemResponse response = discussionItemService.createDiscussionItem(request, gidaiId, user);
		
		URI location = URI.create("/api/discussoin/" + response.getId());
		return ResponseEntity.created(location).body(response);
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<DiscussionItemResponse> edit(@PathVariable Long id, @RequestBody DiscussionItemRequest request) {
		DiscussionItemResponse response = discussionItemService.editDiscussionItem(request, id);
		return ResponseEntity.ok(response);
	}
}
