package com.example.discussion_board.controller.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.dto.LoginRequest;
import com.example.discussion_board.dto.LoginResponse;
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
			LoginResponse loginResponse = authService.login(request);

			// 2. 成功時は ResponseEntity.ok() で返す
			return ResponseEntity.ok(loginResponse);

		} catch (BadCredentialsException e) {
			// 3. 認証失敗は 401 を返す
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of(
							"error", "メールアドレスまたはパスワードが違います"));
		}
	}
}
