package com.example.discussion_board.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.dto.DiscussionItemResponse;
import com.example.discussion_board.service.DiscussionItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/discussion")
public class DiscussionApiController {
	
	private final DiscussionItemService discussionItemService;
	
	@GetMapping("list/{gidaiId}")
	public ResponseEntity<List<DiscussionItemResponse>> getAll(@PathVariable Long gidaiId) {
		List<DiscussionItemResponse> responseItems = discussionItemService.findAllByGidaiId(gidaiId);
		return ResponseEntity.ok(responseItems);
	}
}
