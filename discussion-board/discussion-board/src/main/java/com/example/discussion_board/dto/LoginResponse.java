package com.example.discussion_board.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	//短期有効なJWT
	private String accessToken; 
	//平文トークン
	private String plainRefreshToken;
	//リフレッシュトークンの期限
	private Instant refreshTokenExpiresAt;
}
