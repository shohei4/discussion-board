package com.example.discussion_board.exception.constant;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {
	
	// 401
	AUTH_REQUIRED("AUTH_001", "ログインが必要です", "/login", HttpStatus.UNAUTHORIZED),
	// 403
    ACCESS_DENIED("AUTH_002", "権限がありません", "/", HttpStatus.FORBIDDEN),
    
    // リフレッシュトークン関連 (今回追加分)
    INVALID_TOKEN("AUTH_003", "リフレッシュトークンが無効です", "/login", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("AUTH_004", "リフレッシュトークンの期限が切れています", "/login", HttpStatus.UNAUTHORIZED),
    REVOKED_TOKEN("AUTH_005", "リフレッシュトークンは失効済みです", "/login", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("AUTH_006", "ユーザーが存在しません", "/login", HttpStatus.UNAUTHORIZED),
 // GlobalErrorCodeに追加
    REVOKED_OLD_TOKEN("AUTH_007", "古いリフレッシュトークンは無効です", "/login", HttpStatus.UNAUTHORIZED),
    // 404
    GIDAI_EMPTY("GIDAI_001", "表示できる議題がありません", "/view/gidai/list", HttpStatus.NOT_FOUND);
	
	private final String code;
	private final String message;
	private final String redirectUrl;
	private final HttpStatus status;
	
}
