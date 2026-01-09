package com.example.discussion_board.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.discussion_board.dto.ErrorResponse;
import com.example.discussion_board.exception.AuthException;
import com.example.discussion_board.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHadler {

	//認証エラー(401)
	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ErrorResponse> handleAuth(AuthException e) {
		return ResponseEntity.status(401)
				.body(new ErrorResponse(e.getCode(), e.getMessage(), e.getRedirectUrl()));
	}
	
	//データ不在エラー(404)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> hadleNotFound(ResourceNotFoundException e) {
		return ResponseEntity.status(404)
	            .body(new ErrorResponse(e.getCode(), e.getMessage(), e.getRedirectUrl()));
	}
	 

}
