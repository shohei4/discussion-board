package com.example.discussion_board.service;

import com.example.discussion_board.dto.LikeResultResponse;

public interface ReplyLikeRepository {
	
	/**
	 * いいね登録と解除処理
	 * @param replyId
	 * @param userId
	 * @return true:いいね追加 false:いいね解除
	 */
	public LikeResultResponse toggleLike(Long replyId, Long userId);
	
	/**
	 * いいね数の取得処理
	 * @param replyId
	 * @return
	 */
	public long contLikes(Long replyId);
	
	/**
	 * いいねボタンの押下状態取得処理
	 * @param replyId
	 * @param userId
	 * @return
	 */
	public boolean getIsLiked(Long replyId, Long userId);
}
