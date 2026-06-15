package com.example.discussion_board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyRequest {
	
	private Long commentId;
	@NotBlank(message = "返信は必須です")
	@Size(max = 300, message = "返信コメントは３００文字以内で入力してください")
	private String replyComment;
}
