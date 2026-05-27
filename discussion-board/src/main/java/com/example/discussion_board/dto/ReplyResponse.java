package com.example.discussion_board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyResponse {
	
	private Long id;
	
	private Long commentId;
	
	private UserSummary userSummary;
	
	private String replyComment;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
}
