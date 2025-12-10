package com.example.discussion_board.service;

import java.util.List;

import com.example.discussion_board.dto.DiscussionItemRequest;
import com.example.discussion_board.dto.DiscussionItemResponse;

public interface DiscussionItemService {
	/**
	 * 特定議題の議論コメントを全件取得
	 * @return　レスポンスDTO
	 */
	List<DiscussionItemResponse> findAllByGidaiId(Long gidaiId); 
	
	/**
	 * 議論コメントを登録
	 * @param request
	 * @return レスポンスDTO
	 */
	DiscussionItemResponse createDiscussionItem(DiscussionItemRequest request);
	
	/**
	 * 議論コメントを編集
	 * @param request
	 * @return レスポンスDTO
	 */
	DiscussionItemResponse editDiscussionItem(DiscussionItemRequest request);
	
	/**
	 * 議論コメントの削除
	 * @param id
	 * @return　
	 */
	void deleteDiscussionItem(Long id);
	
}
