package com.example.discussion_board.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.discussion_board.dto.DiscussionItemRequest;
import com.example.discussion_board.dto.DiscussionItemResponse;
import com.example.discussion_board.entity.DiscussionItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DiscussionItemMapper {

	private final ModelMapper modelMapper;
	
	public DiscussionItemResponse toResponse(DiscussionItem discussionItem) {
		DiscussionItemResponse response = modelMapper.map(discussionItem, DiscussionItemResponse.class);
		return response;
	}
	
	public DiscussionItem toEntity(DiscussionItemRequest discussionItemRequest) {
		DiscussionItem entity = modelMapper.map(discussionItemRequest, DiscussionItem.class);
		return entity;
	}

}
