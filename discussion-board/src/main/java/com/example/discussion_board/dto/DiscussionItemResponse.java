package com.example.discussion_board.dto;

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
	private String comment;
	private UserSummary userSummary;
	private GidaiSummary gidaiSummary;
	private Long likeCount;
	private String createdAt;
	private String updatedAt;
}
