package com.example.discussion_board.security;

import com.example.discussion_board.dto.LoginRequest;
import com.example.discussion_board.dto.LoginResponse;
import com.example.discussion_board.dto.RefreshTokenResponse;

public interface AuthService {
	/**
	 * login処理
	 * @param email
	 * @param password
	 * @return 
	 */
	LoginResponse login(LoginRequest loginRequest);
	
	RefreshTokenResponse refeshAccessToken(String refreshToken);
	
	void logout(String refreshToken);
}
