package com.example.discussion_board.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.dto.ReplyRequest;
import com.example.discussion_board.dto.ReplyResponse;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.security.CustomUserDetails;
import com.example.discussion_board.service.ReplyService;
import com.example.discussion_board.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyApiController {
	
	private final ReplyService replyService;
	private final UserService userService;
	
	@GetMapping("/comment/{commentId}")
	public ResponseEntity<List<ReplyResponse>> findAllReply(@PathVariable Long commentId) {
		List<ReplyResponse> responses = replyService.findAllReply(commentId);
		return ResponseEntity.ok(responses);
	}
	
	@PostMapping("/comment/{commentId}")
	public ResponseEntity<ReplyResponse> saveReply(@PathVariable Long commentId,
			@RequestBody ReplyRequest request, @AuthenticationPrincipal CustomUserDetails loginUser) {
		User user = userService.getUserEntityById(loginUser.getId());
		
		ReplyResponse response = replyService.saveReply(request,user, commentId);
		
		return ResponseEntity.status(HttpStatus.CREATED)
		        .body(response);
	}
	
	@PutMapping("/{replyId}")
	public ResponseEntity<ReplyResponse> editReply(@PathVariable Long replyId, @RequestBody ReplyRequest request) {
		ReplyResponse response = replyService.editReply(replyId, request);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}
	
}
