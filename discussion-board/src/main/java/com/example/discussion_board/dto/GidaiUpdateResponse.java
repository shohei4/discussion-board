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
public class GidaiUpdateResponse {
	private Long id;
	private String title;
	private String body;
	private String genre;
	private boolean isDeleted;
	private boolean editable;
	private UserSummary userSummary;
}
