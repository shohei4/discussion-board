package com.example.discussion_board.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenResponse {
	private String AccessToken;
	private String refreshToken;
	private Instant refreshTokenexpiresAt;
}
