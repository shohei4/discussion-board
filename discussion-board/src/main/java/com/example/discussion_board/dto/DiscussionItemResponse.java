package com.example.discussion_board.dto;

import java.time.LocalDateTime;

import com.example.discussion_board.entity.Gidai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscussionItemResponse {

	private Long id;
	private Gidai gidai;
	private String comment;
	private UserSummary userSummary;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
