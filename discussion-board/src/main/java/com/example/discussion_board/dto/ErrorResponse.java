package com.example.discussion_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
/**
 * loginApiControllerで返り値を例外時でも統一するために作成
 */
public class ErrorResponse {

	private String error;

}
