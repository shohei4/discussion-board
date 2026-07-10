package com.example.discussion_board.service.impl;

import org.springframework.stereotype.Service;

import com.example.discussion_board.dto.LikeResultResponse;
import com.example.discussion_board.entity.Reply;
import com.example.discussion_board.entity.ReplyLike;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.repository.ReplyLikeRepository;
import com.example.discussion_board.repository.ReplyRepository;
import com.example.discussion_board.repository.UserRepository;
import com.example.discussion_board.service.LikeService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service("replyLikeService")
@RequiredArgsConstructor
@Transactional
public class ReplyLikeServiceImpl implements LikeService{
	
	private final ReplyLikeRepository repository;
	private final UserRepository userRepository;
	private final ReplyRepository replyRepository;
	
	@Override
	public LikeResultResponse toggleLike(Long replyId, Long userId) {
		// TODO 自動生成されたメソッド・スタブ
		boolean exits = repository
				.existsByReply_IdAndUser_Id(replyId, userId);
		boolean isLikedNow;
		
		if(exits) {
			repository.deleteByReply_IdAndUser_Id(replyId, userId);
			repository.flush();
			isLikedNow = false;
		} else {
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new EntityNotFoundException("User Not Found"));
			
			Reply reply = replyRepository.findById(replyId)
					.orElseThrow(() -> new EntityNotFoundException("Reply Not Found"));
			
			ReplyLike replyLike = ReplyLike.builder()
					.reply(reply)
					.user(user)
					.build();
			repository.saveAndFlush(replyLike);
			isLikedNow = true;
		}
		
		long latestCount = repository.countByReply_Id(replyId);
		return new LikeResultResponse(latestCount, isLikedNow);
	}

	@Override
	public long conuntLikes(Long replyId) {
		// TODO 自動生成されたメソッド・スタブ
		Long count = repository.countByReply_Id(replyId);
		return count != null ? count : 0L;
	}

	@Override
	public boolean getIsLiked(Long replyId, Long userId) {
		// TODO 自動生成されたメソッド・スタブ
		if (userId == null) return false; //未ログインならfalse
		return repository.existsByReply_IdAndUser_Id(replyId, userId);
	}
	
	
}

