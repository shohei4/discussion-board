package com.example.discussion_board.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.LikeResultResponse;
import com.example.discussion_board.dto.ReplyRequest;
import com.example.discussion_board.dto.ReplyResponse;
import com.example.discussion_board.entity.DiscussionItem;
import com.example.discussion_board.entity.Reply;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.mapper.ReplyMapper;
import com.example.discussion_board.repository.DiscussionItemRepository;
import com.example.discussion_board.repository.ReplyRepository;
import com.example.discussion_board.service.CurrentUserService;
import com.example.discussion_board.service.LikeService;
import com.example.discussion_board.service.ReplyService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	
	private final ReplyRepository replyRepository;
	private final DiscussionItemRepository discussionItemRepository;
	private final ReplyMapper mapper;
	private final CurrentUserService currentUserService;
	private final LikeService replyLikeService;
	
	@Override
	public List<ReplyResponse> findAllReply(Long discussionItemId, Long userId) {
	    // TODO 自動生成されたメソッド・スタブ
	    List<ReplyResponse> responses = replyRepository.findByDiscussionItem_IdAndIsDeletedFalse(discussionItemId)
	        .stream()
	        .map(reply -> {
	            ReplyResponse response = mapper.toResponse(reply); // ①DTOへ変換
	            response.setEditable(currentUserService.isOwner(reply.getUser().getId())); // ②のeditable設定

	            //いいね情報の取得
	            Long replyLikeCount = replyLikeService.conuntLikes(reply.getId());
	            Boolean replyIsLiked = replyLikeService.getIsLiked(reply.getId(), userId);
	            LikeResultResponse replyLikeResult = new LikeResultResponse(replyLikeCount, replyIsLiked);
	            response.setLikeResult(replyLikeResult);

	            return response;
	        })
	        .toList();
	    return responses;
	}
	@Override
	public ReplyResponse saveReply(ReplyRequest request,User user, Long discussionItemId) {
		// TODO 自動生成されたメソッド・スタブ
		DiscussionItem discussionItem = discussionItemRepository.findById(discussionItemId)
				.orElseThrow(() -> new RuntimeException("議論コメントが見つかりません"));
				
		Reply reply = mapper.toEntity(request, user, discussionItem);
		replyRepository.save(reply);
		ReplyResponse response = mapper.toResponse(reply);
		response.setEditable(true);
		return response;
	}
	@Override
	public ReplyResponse editReply(Long replyId, ReplyRequest request) {
		// TODO 自動生成されたメソッド・スタブ
		Reply reply = replyRepository.findById(replyId)
				.orElseThrow(() -> new RuntimeException("返信コメントが見つかりません"));
		
		reply.setReplyComment(request.getReplyComment());
		
		replyRepository.saveAndFlush(reply);
		
		ReplyResponse response = mapper.toResponse(reply);
		
		response.setEditable(true);
		return response;
	}
	
	
	
	
}
