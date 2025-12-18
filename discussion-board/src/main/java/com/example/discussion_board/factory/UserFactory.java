package com.example.discussion_board.factory;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.discussion_board.entity.User;

@Component
public class UserFactory {
	
	private final PasswordEncoder passwordEncoder;
	
	//コンストラクタ
	public UserFactory(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	//基本的なユーザー作成
	public User create(String username, String email, String rawpassword) {
		//ハッシュ化
		String hashPassword = passwordEncoder.encode(rawpassword);
		
		return User.builder()
				.username(username)
				.email(email)
				.password(hashPassword)
				.build();
	}

}
