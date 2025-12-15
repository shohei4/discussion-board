package com.example.discussion_board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GidaiRegistrationRequest {
	
    private Long id;
    private String title;   // 議題タイトル
    private String body;    // 本文
    private String genre;   // ジャンル
}
