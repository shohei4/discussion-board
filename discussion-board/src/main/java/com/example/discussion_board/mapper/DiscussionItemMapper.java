package com.example.discussion_board.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.discussion_board.dto.DiscussionItemRequest;
import com.example.discussion_board.dto.DiscussionItemResponse;
import com.example.discussion_board.dto.DiscussionItemWithReplyResponse;
import com.example.discussion_board.dto.GidaiSummary;
import com.example.discussion_board.dto.LikeResultResponse;
import com.example.discussion_board.dto.ReplyResponse;
import com.example.discussion_board.dto.UserSummary;
import com.example.discussion_board.entity.DiscussionItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DiscussionItemMapper {

	private final ModelMapper modelMapper;
	
	//返信も含めたMapperメソッド
	public DiscussionItemWithReplyResponse toResponseWithReplies(
	        DiscussionItem discussionItem,
	        List<ReplyResponse> replies,
	        LikeResultResponse likeResult,
	        Boolean editable) {

	    UserSummary userSummary = new UserSummary(discussionItem.getUser().getId(), discussionItem.getUser().getUsername());
	    GidaiSummary gidaiSummary = new GidaiSummary(discussionItem.getGidai().getId(),
	                                                 discussionItem.getGidai().getTitle(),
	                                                 discussionItem.getGidai().getBody());

	    DiscussionItemWithReplyResponse response =
	            DiscussionItemWithReplyResponse.builder()
	                .id(discussionItem.getId())
	                .comment(discussionItem.getComment())
	                .userSummary(userSummary)
	                .gidaiSummary(gidaiSummary)
	                .createdAt(discussionItem.getCreatedAt())
	                .updatedAt(discussionItem.getUpdatedAt())
	                .replies(replies)
	                .likeResult(likeResult)
	                .editable(editable)
	                .build();
	    return response;
	}
	
	public DiscussionItemResponse toResponse(DiscussionItem discussionItem, LikeResultResponse likeResult, Boolean editable) {
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
									.likeResult(likeResult)
									.editable(editable)
									.createdAt(discussionItem.getCreatedAt())
									.updatedAt(discussionItem.getUpdatedAt())
									.build();
		return response;
	}
	
	//更新時のMapperメソッド
	public DiscussionItemResponse toResponse(DiscussionItem discussionItem) {
		
		DiscussionItemResponse response = DiscussionItemResponse.builder()
									.id(discussionItem.getId())
									.comment(discussionItem.getComment())
									.createdAt(discussionItem.getCreatedAt())
									.updatedAt(discussionItem.getUpdatedAt())
									.build();
		return response;
	}
	
	public DiscussionItem toEntity(DiscussionItemRequest discussionItemRequest) {
		DiscussionItem entity = modelMapper.map(discussionItemRequest, DiscussionItem.class);
		return entity;
	}

}
