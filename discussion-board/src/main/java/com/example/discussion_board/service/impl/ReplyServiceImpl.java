package com.example.discussion_board.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.ReplyRequest;
import com.example.discussion_board.dto.ReplyResponse;
import com.example.discussion_board.entity.Reply;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.mapper.ReplyMapper;
import com.example.discussion_board.repository.ReplyRepository;
import com.example.discussion_board.service.ReplyService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	
	private final ReplyRepository repository;
	private final ReplyMapper mapper;
	@Override
	public List<ReplyResponse> findAllReply(Long commentId) {
		// TODO 自動生成されたメソッド・スタブ
		List<ReplyResponse> responses = repository.findByCommentIdAndIsDeletedFalse(commentId)
		.stream()
		.map(mapper::toResponse)
		.collect(Collectors.toList());
		return responses;
	}
	@Override
	public ReplyResponse saveReply(ReplyRequest request,User user, Long commentId) {
		// TODO 自動生成されたメソッド・スタブ
		Reply reply = mapper.toEntity(request, user, commentId);
		repository.save(reply);
		ReplyResponse response = mapper.toResponse(reply);
		return response;
	}
	@Override
	public ReplyResponse updateReply(Long replyId, ReplyRequest request) {
		// TODO 自動生成されたメソッド・スタブ
		Reply reply = repository.findById(replyId)
				.orElseThrow(() -> new RuntimeException("返信コメントが見つかりません"));
		
		reply.setReplyComment(request.getReplyComment());
		
		repository.save(reply);
		return mapper.toResponse(reply);
	}
	
	
	
	
}
