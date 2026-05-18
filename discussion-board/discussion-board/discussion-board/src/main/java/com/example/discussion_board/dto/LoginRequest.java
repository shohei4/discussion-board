package com.example.discussion_board.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
	
	@NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メールアドレスの形式が正しくありません")
	private String email;
	@NotBlank(message = "パスワードは必須です")
	@Size(min = 8, max = 100, message = "パスワードは8文字以上で入力してください")
	private String password;

}
