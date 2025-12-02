package com.example.discussion_board.controller.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.dto.LoginRequest;
import com.example.discussion_board.dto.LoginResponse;
import com.example.discussion_board.dto.LogoutResponse;
import com.example.discussion_board.dto.RefreshTokenRequest;
import com.example.discussion_board.exception.AuthException;
import com.example.discussion_board.security.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginApiController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
			// 1. 認証
			String email = request.getEmail();
			String password = request.getPassword();
			LoginResponse loginResponse = authService.login(email, password);

			// 2. 成功時は ResponseEntity.ok() で返す
			return ResponseEntity.ok(loginResponse);
		} catch (AuthException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of(
							"error", e.getCode(),
							"message", e.getMessage()));
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest request) {
		String plainToken = request.getPlainRefreshToken();
		authService.logout(plainToken);

		//成功メッセージを返却
		return ResponseEntity.ok(new LogoutResponse("ログアウトに成功しました"));
	}
}
