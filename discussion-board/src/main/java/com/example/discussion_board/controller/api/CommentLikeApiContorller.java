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
import com.example.discussion_board.service.CommentLikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class CommentLikeApiContorller {

	private final CommentLikeService commentLikeService;

	@PostMapping("/toggle/{commentId}")
	public ResponseEntity<LikeResultResponse> toggleLike(@PathVariable Long commentId,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		//1. キャスト(引数で指定すればSpringが自動で型チェックしてくれる)
		if (customUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		//2. CustomUserDetailsが持っているidを取得
		Long userId = customUser.getId();

		LikeResultResponse result = commentLikeService.toggleLike(commentId, userId);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/count/{commentId}")
	public ResponseEntity<LikeResultResponse> getLikeCount(@PathVariable Long commentId,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		//1. キャスト(引数で指定すればSpringが自動で型チェックしてくれる)
		if (customUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		//2. CustomUserDetailsが持っているidを取得
		Long userId = customUser.getId();

		Long count = commentLikeService.conuntLikes(commentId);		
		boolean isLiked = commentLikeService.getIsLiked(commentId, userId);
		
		LikeResultResponse result = new LikeResultResponse(count, isLiked);
		return ResponseEntity.ok(result);
	}
}
