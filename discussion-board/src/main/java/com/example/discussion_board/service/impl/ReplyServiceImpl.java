package com.example.discussion_board.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.ReplyRequest;
import com.example.discussion_board.dto.ReplyResponse;
import com.example.discussion_board.mapper.ReplyMapper;
import com.example.discussion_board.repository.GidaiRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl {
	
	private final GidaiRepository repository;
	private final ReplyMapper mapper;
	
	public ReplyResponse createReply(ReplyRequest request) {
		
	}
	
}
