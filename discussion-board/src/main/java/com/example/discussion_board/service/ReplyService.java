package com.example.discussion_board.service;

import java.util.List;

import com.example.discussion_board.dto.ReplyRequest;
import com.example.discussion_board.dto.ReplyResponse;

public interface ReplyService {
	
	public List<ReplyResponse> findAllReply(Long id);
	
	public List<ReplyResponse> saveReply(ReplyRequest reply);
	
	public List<ReplyResponse> editReply(ReplyRequest reply);
	
} 
