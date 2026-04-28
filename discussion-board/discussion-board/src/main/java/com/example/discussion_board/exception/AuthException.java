package com.example.discussion_board.exception;

import com.example.discussion_board.exception.constant.GlobalErrorCode;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private final GlobalErrorCode errorCode;
    
    public AuthException (GlobalErrorCode errorCode) {
    	super(errorCode.getMessage());
    	this.errorCode = errorCode;
    }
}

