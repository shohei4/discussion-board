package com.example.discussion_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeResultResponse {
	private long likeCount;
	private boolean isLiked;
}
