package com.example.discussion_board.factory;

import com.example.discussion_board.dto.GidaiResponse;
import com.example.discussion_board.entity.Gidai;

public abstract class BaseResponseFactory {
	
	/**
	 * 投稿レスポンスを生成する共通メソッド
	 */
	public abstract GidaiResponse createGidaiResponse(Gidai gidai);
}
