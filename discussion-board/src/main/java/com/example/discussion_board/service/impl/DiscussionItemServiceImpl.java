package com.example.discussion_board.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.DiscussionItemRequest;
import com.example.discussion_board.dto.DiscussionItemResponse;
import com.example.discussion_board.repository.DiscussionItemRepository;
import com.example.discussion_board.service.DiscussionItemService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscussionItemServiceImpl implements DiscussionItemService {
	
	private final DiscussionItemRepository repository;
	
	@Override
	public List<DiscussionItemResponse> findAllByGidaiId(Long gidaiId) {
		// TODO 自動生成されたメソッド・スタブ
		repository.findAllByGidaiId(gidaiId);
		return null;
	}

	@Override
	public DiscussionItemResponse createDiscussionItem(DiscussionItemRequest request) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public DiscussionItemResponse editDiscussionItem(DiscussionItemRequest request) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void deleteDiscussionItem(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
