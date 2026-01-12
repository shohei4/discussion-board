package com.example.discussion_board.controller.api;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.dto.ErrorResponse;
import com.example.discussion_board.dto.LoginRequest;
import com.example.discussion_board.dto.LoginResponse;
import com.example.discussion_board.dto.LogoutResponse;
import com.example.discussion_board.dto.RefreshTokenRequest;
import com.example.discussion_board.exception.AuthException;
import com.example.discussion_board.exception.constant.GlobalErrorCode;
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
			
			//2. RefreshTokenをHttpOnly Cokkieとして構築
			ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", loginResponse.getPlainRefreshToken())
					.httpOnly(true) // JSからアクセス不可になる
					.secure(false)   // HTTPSのみ
					.path("/")      // サイト全体で有効
					.maxAge(14 * 24 * 60 * 60) //14日間
					.sameSite("Strict") // CSRF対策
					.build();
			// 3. レスポンスの構築
			Map<String, Object> body = Map.of(
				"accessToken", loginResponse.getAccessToken(),
				"refreshTokenExpiresAt", loginResponse.getRefreshTokenExpiresAt()
			);
					
			return ResponseEntity.ok()
					.header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString()) //Cookieをセット
					.body(body);
		} catch (AuthException e) {
			GlobalErrorCode errorCode = e.getErrorCode();
			
			return ResponseEntity
					.status(errorCode.getStatus())
					.body(new ErrorResponse(
							errorCode.getCode(),
							errorCode.getMessage(),
							errorCode.getRedirectUrl()
							));
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
