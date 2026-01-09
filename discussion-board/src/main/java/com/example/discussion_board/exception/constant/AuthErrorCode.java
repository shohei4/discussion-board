package com.example.discussion_board.exception.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {

	AUTH_REQUIRED("AUTH_001", "ログインが必要です", "/login"),
    ACCESS_DENIED("AUTH_002", "権限がありません", "/"),
    GIDAI_EMPTY("GIDAI_001", "表示できる議題がありません", "/view/gidai/list");
	
	private final String code;
	private final String message;
	private final String redirectUrl;

	
}
