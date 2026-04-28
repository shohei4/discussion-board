package com.example.discussion_board.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.LikeResultResponse;
import com.example.discussion_board.entity.CommentLike;
import com.example.discussion_board.entity.DiscussionItem;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.repository.CommentLikeRepository;
import com.example.discussion_board.repository.DiscussionItemRepository;
import com.example.discussion_board.repository.UserRepository;
import com.example.discussion_board.service.CommentLikeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

	private final CommentLikeRepository repository;
	private final UserRepository userRepository;
	private final DiscussionItemRepository discussionItemRepository;
	
	@Transactional
	@Override
	public LikeResultResponse toggleLike(Long commentId, Long userId) {
		// TODO 自動生成されたメソッド・スタブ
		boolean exists = repository
				.existsByDiscussionItem_IdAndUser_Id(commentId, userId);
		boolean isLikedNow;
		
		if (exists) {
			repository.deleteByDiscussionItem_IdAndUser_Id(commentId, userId);
			repository.flush(); // ★ここ重要：削除を即座に反映
			isLikedNow = false;
			
		} else {
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new EntityNotFoundException("User not found"));

			DiscussionItem item = discussionItemRepository.findById(commentId)
					.orElseThrow(() -> new EntityNotFoundException("Comment not found"));
			
			CommentLike commentLike = CommentLike.builder()
					.discussionItem(item)
					.user(user)
					.build();
			repository.saveAndFlush(commentLike); // ★ここ重要：保存を即座に反映
			isLikedNow = true;
		}
		
		long latestCount = repository.countByDiscussionItem_Id(commentId);
		
		//カウントと状態をセットにして返す
		return new LikeResultResponse(latestCount, isLikedNow);
	}

	@Override
	public long conuntLikes(Long commentId) {
		// TODO 自動生成されたメソッド・スタブ
		Long count = repository.countByDiscussionItem_Id(commentId);
		//nullチェック
		return count != null ? count : 0L;
	}

	@Override
	public boolean getIsLiked(Long commentId, Long userId) {
		// TODO 自動生成されたメソッド・スタブ
		if (userId == null) return false; //未ログインならfalse
		return repository.existsByDiscussionItem_IdAndUser_Id(commentId, userId);
	}

}
