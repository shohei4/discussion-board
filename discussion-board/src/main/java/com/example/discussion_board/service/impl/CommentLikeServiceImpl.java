package com.example.discussion_board.service.impl;

import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

	private final CommentLikeRepository repository;
	private final UserRepository userRepository;
	private final DiscussionItemRepository discussionItemRepository;

	@Override
	public boolean toggleLike(Long commentId, Long userId) {
		// TODO 自動生成されたメソッド・スタブ
		boolean exists = repository
				.existsByDiscussionItem_IdAndUser_Id(commentId, userId);

		if (exists) {
			repository.deleteByDiscussionItem_IdAndUser_Id(commentId, userId);
			return false;//いいね解除
		} else {
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new EntityNotFoundException("User not found"));

			DiscussionItem item = discussionItemRepository.findById(commentId)
					.orElseThrow(() -> new EntityNotFoundException("Comment not found"));
			
			CommentLike commentLike = CommentLike.builder()
					.discussionItem(item)
					.user(user)
					.build();
			repository.save(commentLike);
			return true;
		}
	}

	@Override
	public long conuntLikes(Long commentId) {
		// TODO 自動生成されたメソッド・スタブ
		Long count = repository.countByDiscussionItem_Id(commentId);
		//nullチェック
		return count != null ? count : 0L;
	}

}
