package com.example.discussion_board.controller.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.dto.RefreshTokenRequest;
import com.example.discussion_board.dto.RefreshTokenResponse;
import com.example.discussion_board.exception.AuthException;
import com.example.discussion_board.security.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {
	/** DI*/
	private final AuthService authService;
	
	@PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request) {
		
        try {
        	String plainToken = request.getPlainRefreshToken();
        	RefreshTokenResponse response = authService.refreshAccessToken(plainToken);
            return ResponseEntity.ok(response);     

        } catch (AuthException e) {
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error", e.getCode(),
                            "message", e.getMessage()
                    ));
        }
}
}
