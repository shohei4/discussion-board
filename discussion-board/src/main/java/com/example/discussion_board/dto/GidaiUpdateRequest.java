package com.example.discussion_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GidaiUpdateRequest {
	
	private Long id;
	private String title;
	private String body;
	private String genre;
	private Boolean isDeleted;
}
