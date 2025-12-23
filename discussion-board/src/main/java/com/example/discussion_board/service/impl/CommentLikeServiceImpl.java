package com.example.discussion_board.service.impl;

import org.springframework.stereotype.Service;

import com.example.discussion_board.entity.CommentLike;
import com.example.discussion_board.entity.DiscussionItem;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.repository.CommentLikeRepository;
import com.example.discussion_board.service.CommentLikeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService{
	
	private final CommentLikeRepository repository;
	
	@Override
	public boolean toggeLike(Long commentId, Long userId) {
		// TODO 自動生成されたメソッド・スタブ
		boolean exists = repository
				.existsByDiscussionItem_IdAndUser_Id(commentId, userId);
		
		if(exists) {
			repository.deleteByDiscussionItem_IdAndUser_Id(commentId, userId);
			return false;//いいね解除
		} else {
			CommentLike commentLike = CommentLike.builder()
					.discussionItem(new DiscussionItem(commentId))
					.user(new User(userId))
					.build();
			repository.save(commentLike);
			return true;
		}
	}

	@Override
	public long conuntLikes(Long commentId) {
		// TODO 自動生成されたメソッド・スタブ
		Long count = repository.countByDiscussionItem_Id(commentId);
		return count;
	}


}
