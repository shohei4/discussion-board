package com.example.discussion_board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GidaiRequest {
	
    private String gidai;   // 議題名
    private String genre;   // ジャンル
}
