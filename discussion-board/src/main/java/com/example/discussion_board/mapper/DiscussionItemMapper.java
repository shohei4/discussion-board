package com.example.discussion_board.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.discussion_board.dto.DiscussionItemRequest;
import com.example.discussion_board.dto.DiscussionItemResponse;
import com.example.discussion_board.dto.GidaiSummary;
import com.example.discussion_board.dto.UserSummary;
import com.example.discussion_board.entity.DiscussionItem;
import com.example.discussion_board.util.DateTimeFormats;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DiscussionItemMapper {

	private final ModelMapper modelMapper;
	
	public DiscussionItemResponse toResponse(DiscussionItem discussionItem, long likeCount, boolean isLiked) {
		//簡易ユーザークラス作成
		UserSummary userSummary = new UserSummary(discussionItem.getUser().getId(), discussionItem.getUser().getUsername());
		//簡易議題クラス作成
		GidaiSummary gidaiSummary = new GidaiSummary(discussionItem.getGidai().getId(), 
													 discussionItem.getGidai().getTitle(), 
													 discussionItem.getGidai().getBody());
		
		DiscussionItemResponse response = DiscussionItemResponse.builder()
									.id(discussionItem.getId())
									.comment(discussionItem.getComment())
									.gidaiSummary(gidaiSummary)
									.userSummary(userSummary)
									.likeCount(likeCount)
									.isLiked(isLiked)
									.createdAt(discussionItem.getCreatedAt().format(DateTimeFormats.JAPAN))
									.updatedAt(discussionItem.getUpdatedAt().format(DateTimeFormats.JAPAN))
									.build();
		return response;
	}
	
	public DiscussionItem toEntity(DiscussionItemRequest discussionItemRequest) {
		DiscussionItem entity = modelMapper.map(discussionItemRequest, DiscussionItem.class);
		return entity;
	}

}
