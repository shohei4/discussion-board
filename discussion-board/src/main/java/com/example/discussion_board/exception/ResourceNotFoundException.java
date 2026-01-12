package com.example.discussion_board.exception;

import com.example.discussion_board.exception.constant.GlobalErrorCode;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
	
	private final GlobalErrorCode errorCode;
	
	public ResourceNotFoundException(GlobalErrorCode errorCode) {
		// TODO 自動生成されたコンストラクター・スタブ
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}
