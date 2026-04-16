package com.example.discussion_board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GidaiUpdateRequest {
	
	private Long id;
	@NotBlank(message = "タイトルは必須です")
	@Size(max = 100, message = "タイトルは１００文字以内で入力してください")
	private String title;
	@NotBlank(message = "本文は必須です")
	@Size(max = 1000, message = "本文は１０００文字以内で入力してください")
	private String body;
	private String genre;
	private Boolean isDeleted;
}
