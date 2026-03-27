package com.example.discussion_board.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	 
	//入力バリデーション用のハンドラー
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
		
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(
				error -> {
					errors.put(
							error.getField(),
							error.getDefaultMessage()
							);
				}
				);
		
		 ErrorResponse response = new ErrorResponse(
		            "VALIDATION_ERROR",
		            "入力値に誤りがあります",
		            errors
		    );
		 
		 return ResponseEntity.badRequest().body(response);
	}	
}
