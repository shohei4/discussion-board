package com.example.discussion_board.dto;

import com.example.discussion_board.entity.Gidai;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GidaiSummary {
	private Long id;
	private String title;
	private String body;
	
	 public static GidaiSummary from(Gidai gidai) {
	        return new GidaiSummary(
	            gidai.getId(),
	            gidai.getTitle(),
	            gidai.getBody()
	        );
	    }
}
