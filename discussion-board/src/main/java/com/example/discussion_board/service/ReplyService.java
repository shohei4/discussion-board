package com.example.discussion_board.service;

import java.util.List;

import com.example.discussion_board.dto.ReplyRequest;
import com.example.discussion_board.dto.ReplyResponse;
import com.example.discussion_board.entity.User;

public interface ReplyService {
	
	public List<ReplyResponse> findAllReply(Long id);
	
	public ReplyResponse saveReply(ReplyRequest request,User user,Long comnetId);
	
	public ReplyResponse updateReply(Long replyId, ReplyRequest request);
	
} 
