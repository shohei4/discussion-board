package com.example.discussion_board.service;

import com.example.discussion_board.dto.LikeResultResponse;

public interface CommentLikeService {
	
	/**
	 * いいね登録と解除処理
	 * @param commentId
	 * @param userId
	 * @return true:いいね追加 false:いいね解除
	 */
	public LikeResultResponse toggleLike(Long commentId, Long userId);
	
	/**
	 * いいね数の取得処理
	 * @param commentId
	 * @return Long (特定commnetIdの登録数)
	 */
	public long conuntLikes(Long commentId);
	
	/**
	 * ユーザーがいいねボタンの押下状態の取得
	 * @param commentId
	 * @param userId
	 * @return　boolean true：押下　false；未押下
	 */
	public boolean getIsLiked(Long commentId, Long userId);
	
}
