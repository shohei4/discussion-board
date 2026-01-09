package com.example.discussion_board.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
	
	private final String code;
	private final String redirectUrl;
	
	public ResourceNotFoundException(String message, String code, String redirectUrl) {
		// TODO 自動生成されたコンストラクター・スタブ
		super(message);
		this.code = code;
		this.redirectUrl = redirectUrl;
	}

}
