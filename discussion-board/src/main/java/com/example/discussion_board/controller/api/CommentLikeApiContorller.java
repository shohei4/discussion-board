package com.example.discussion_board.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.service.CommentLikeService;
import com.example.discussion_board.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class CommentLikeApiContorller {
	
	private final CommentLikeService commentLikeService;
	private final UserService userService;
	
	@PostMapping("/toggle/{commentId}")
	public ResponseEntity<Boolean> toggleLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails user) {
		Long userId = userService.findIdByEmail(user.getUsername());
		//サービス返値をそのままレスポンスボディ二入れる
		boolean isLiked = commentLikeService.toggleLike(commentId, userId);
		return ResponseEntity.ok(isLiked);
	}
	
	@GetMapping("/count/{commentId}")
	public ResponseEntity<Long> getLikeCount(@PathVariable Long commentId) {
		Long count = commentLikeService.conuntLikes(commentId);
		return ResponseEntity.ok(count);
	}
}
