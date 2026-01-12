package com.example.discussion_board.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.discussion_board.dto.ErrorResponse;
import com.example.discussion_board.exception.AuthException;
import com.example.discussion_board.exception.ResourceNotFoundException;
import com.example.discussion_board.exception.constant.GlobalErrorCode;

@RestControllerAdvice
public class GlobalExceptionHadler {

	//認証エラー(401)
	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ErrorResponse> handleAuth(AuthException e) {
		// 1. 例外からEnum（ErrorCode)を取り出す
		GlobalErrorCode errorCode = e.getErrorCode();
		
		// 2. 固定値の（403）をerrorCode.getStatus()に変更する
		return ResponseEntity
				.status(errorCode.getStatus())
				.body(new ErrorResponse(
						errorCode.getCode(),
						errorCode.getMessage(),
						errorCode.getRedirectUrl()
						));
	}
	
	//データ不在エラー(404)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> hadleNotFound(ResourceNotFoundException e) {
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
