package com.example.discussion_board.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	
	private Long id;
	@NotBlank(message = "ユーザー名は必須です")
    @Size(min = 3, max = 30, message = "ユーザー名は３から３０文字以内で入力してください")
	private String username;
	@NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メールアドレスの形式が正しくありません")
	private String email;
	@NotBlank(message = "パスワードは必須です")
	@Size(min = 8, max = 100, message = "パスワードは8文字以上で入力してください")
	private String password;

}
