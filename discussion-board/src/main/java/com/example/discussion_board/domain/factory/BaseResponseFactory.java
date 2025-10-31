package com.example.discussion_board.domain.factory;

import com.example.discussion_board.domain.entity.Gidai;
import com.example.discussion_board.dto.GidaiResponse;

public abstract class BaseResponseFactory {
	
	/**
	 * 投稿レスポンスを生成する共通メソッド
	 */
	public abstract GidaiResponse createGidaiResponse(Gidai gidai);
}
