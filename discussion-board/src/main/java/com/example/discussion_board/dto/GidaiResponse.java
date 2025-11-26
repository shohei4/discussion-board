package com.example.discussion_board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GidaiResponse {

	private Long id;
	private String gidai;
	private String genre;
	private UserSummary userSummary;

}
