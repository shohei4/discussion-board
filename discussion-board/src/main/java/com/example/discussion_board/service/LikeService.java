package com.example.discussion_board.service;

import com.example.discussion_board.dto.LikeResultResponse;

public interface LikeService {
	
	/**
	 * いいね登録と解除処理
	 * @param commentId
	 * @param userId
	 * @return true:いいね追加 false:いいね解除
	 */
	public LikeResultResponse toggleLike(Long targetId, Long userId);
	
	/**
	 * いいね数の取得処理
	 * @param commentId
	 * @return Long (特定の対象の登録数)
	 */
	public long conuntLikes(Long targetId);
	
	/**
	 * ユーザーがいいねボタンの押下状態の取得
	 * @param commentId
	 * @param userId
	 * @return　boolean true：押下　false；未押下
	 */
	public boolean getIsLiked(Long targetId, Long userId);
	
}
