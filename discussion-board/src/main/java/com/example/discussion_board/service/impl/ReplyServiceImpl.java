package com.example.discussion_board.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.ReplyRequest;
import com.example.discussion_board.dto.ReplyResponse;
import com.example.discussion_board.entity.DiscussionItem;
import com.example.discussion_board.entity.Reply;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.mapper.ReplyMapper;
import com.example.discussion_board.repository.DiscussionItemRepository;
import com.example.discussion_board.repository.ReplyRepository;
import com.example.discussion_board.service.ReplyService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	
	private final ReplyRepository replyRepository;
	private final DiscussionItemRepository discussionItemRepository;
	private final ReplyMapper mapper;
	@Override
	public List<ReplyResponse> findAllReply(Long discussionItemId) {
		// TODO 自動生成されたメソッド・スタブ
		List<ReplyResponse> responses = replyRepository.findByDiscussionItem_IdAndIsDeletedFalse(discussionItemId)
		.stream()
		.map(mapper::toResponse)
		.collect(Collectors.toList());
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
