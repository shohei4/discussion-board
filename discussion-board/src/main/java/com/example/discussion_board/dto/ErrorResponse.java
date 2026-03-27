package com.example.discussion_board.dto;

import java.util.Map;

import lombok.Data;

@Data
/**
 * loginApiControllerで返り値を例外時でも統一するために作成
 * バリデーションを受け取れるようにerrorsフィールドを追加
 */
public class ErrorResponse {
	
	private String code;
	private String message;
	private String redirectUrl;
	private Map<String, String> errors;
	
	 // コンストラクタ
    public ErrorResponse(String code, String message, String redirectUrl) {
        this.code = code;
        this.message = message;
        this.redirectUrl = redirectUrl;
    }

    public ErrorResponse(String code, String message, Map<String, String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

}
