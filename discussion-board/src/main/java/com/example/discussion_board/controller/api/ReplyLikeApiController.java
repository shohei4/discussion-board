package com.example.discussion_board.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.dto.LikeResultResponse;
import com.example.discussion_board.security.CustomUserDetails;
import com.example.discussion_board.service.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply-like")
public class ReplyLikeApiController {
	
	private final LikeService replyLikeService;
	
	@PostMapping("/toggle/{replyId}")
	public ResponseEntity<LikeResultResponse> toggleLike(@PathVariable Long replyId,
			@AuthenticationPrincipal CustomUserDetails userDetails){
		if(userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Long userId = userDetails.getId();
		
		LikeResultResponse response = replyLikeService.toggleLike(replyId, userId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/count/{replyId}")
	public ResponseEntity<LikeResultResponse> getLikeCount(@PathVariable Long replyId,
	@AuthenticationPrincipal CustomUserDetails userDetails) {
		if(userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Long userId = userDetails.getId();
		
		Long count = replyLikeService.conuntLikes(replyId);
		boolean isLiked = replyLikeService.getIsLiked(replyId, userId);
		
		LikeResultResponse response = new LikeResultResponse(count, isLiked);
		return ResponseEntity.ok(response);
	}
}
