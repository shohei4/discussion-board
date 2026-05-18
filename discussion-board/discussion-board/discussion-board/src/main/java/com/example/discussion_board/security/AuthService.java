package com.example.discussion_board.security;

import com.example.discussion_board.dto.LoginResponse;
import com.example.discussion_board.dto.RefreshTokenResponse;

public interface AuthService {
	/**
	 * login処理
	 * @param email
	 * @param password
	 * @return 
	 */
	LoginResponse login(String Email, String password);
	
	/**
	 * 再発行処理
	 * @param refreshToken
	 * @return　RefreshTokenResponse(accessToken, plainRefreshToekn, refreshTokenexpiresAt)
	 */
	RefreshTokenResponse refreshAccessToken(String refreshToken);
	
	/**
	 * ログアウト処理（特定のリフレッシュトークンを無効にする）
	 * @param plainToken
	 */
	void logout(String plainToken);
}
