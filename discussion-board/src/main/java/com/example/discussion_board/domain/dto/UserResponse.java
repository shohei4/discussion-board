package com.example.discussion_board.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
	
	private Long id;
	private String username;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
