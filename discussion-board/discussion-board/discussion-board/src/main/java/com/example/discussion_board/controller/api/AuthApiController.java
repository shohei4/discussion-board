package com.example.discussion_board.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discussion_board.dto.ErrorResponse;
import com.example.discussion_board.dto.RefreshTokenRequest;
import com.example.discussion_board.dto.RefreshTokenResponse;
import com.example.discussion_board.exception.AuthException;
import com.example.discussion_board.exception.constant.GlobalErrorCode;
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
        	GlobalErrorCode errorCode = e.getErrorCode();
        	return  ResponseEntity
 					.status(errorCode.getStatus())
 					.body(new ErrorResponse(
 							errorCode.getCode(),
 							errorCode.getMessage(),
 							errorCode.getRedirectUrl()
 							));
        }
}
}
