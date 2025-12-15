package com.example.discussion_board.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.DiscussionItemRequest;
import com.example.discussion_board.dto.DiscussionItemResponse;
import com.example.discussion_board.entity.DiscussionItem;
import com.example.discussion_board.entity.Gidai;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.mapper.DiscussionItemMapper;
import com.example.discussion_board.repository.DiscussionItemRepository;
import com.example.discussion_board.repository.GidaiRepository;
import com.example.discussion_board.repository.UserRepository;
import com.example.discussion_board.service.DiscussionItemService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscussionItemServiceImpl implements DiscussionItemService {
	
	private final DiscussionItemRepository discussionItemRepository;
	private final UserRepository userRepository;
	private final GidaiRepository gidaiRepository;
	private final DiscussionItemMapper discussionItemMapper;
	
	@Override
	public List<DiscussionItemResponse> findAllByGidaiId(Long gidaiId) {
		// TODO 自動生成されたメソッド・スタブ
		List<DiscussionItemResponse> responses = 
				discussionItemRepository.findAllByGidaiId(gidaiId)
					.stream()
					.map(discussionItemMapper::toResponse)
					.toList();
		return responses;
	}

	@Override
	public DiscussionItemResponse createDiscussionItem(DiscussionItemRequest request, Long gidaiId, Long userId) {
		// TODO 自動生成されたメソッド・スタブ
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));;
		Gidai gidai = gidaiRepository.findById(gidaiId)
				.orElseThrow(() -> new IllegalArgumentException(
		                "指定された議題IDが存在しません: " + gidaiId));;
		DiscussionItem discussionItem = DiscussionItem.builder()
		.comment(request.getComment())
		.gidai(gidai)
		.user(user)
		.build();
		discussionItemRepository.save(discussionItem);
		DiscussionItemResponse response = discussionItemMapper.toResponse(discussionItem);
		return response;
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
