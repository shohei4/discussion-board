package com.example.discussion_board.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private final String code;
    private final String redirectUrl;

    public AuthException(String code, String message, String redirectUrl) {
        super(message);
        this.code = code;
        this.redirectUrl = redirectUrl;
    }
}

