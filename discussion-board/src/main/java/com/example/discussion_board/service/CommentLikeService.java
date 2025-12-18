package com.example.discussion_board.service;

public interface CommentLikeService {
	
	/**
	 * いいね登録と解除処理
	 * @param commentId
	 * @param userId
	 * @return true:いいね追加 false:いいね解除
	 */
	public boolean toggeLike(Long commentId, Long userId);
	
	/**
	 * いいね数の取得処理
	 * @param commentId
	 * @return Long (特定commnetIdの登録数)
	 */
	public long conuntLikes(Long commentId);
	
}
