package com.example.discussion_board.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.discussion_board.dto.ReplyResponse;
import com.example.discussion_board.dto.UserSummary;
import com.example.discussion_board.entity.Reply;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReplyMapper {
	
	private final ModelMapper modelMapper;
	
	 public ReplyResponse toResponse(Reply reply) {
	        User user = reply.getUser();
	        UserSummary userSummary = new UserSummary(Reply.getUser(),user.getId(), user.getUsername());
	        return ReplyResponse.builder()
	                .id(reply.getId())
	                .commentId(reply.getCommentId())
	                .userSummary(userSummary)
	                .replyComment(reply.getReplyComment())
	                .createdAt(reply.getCreatedAt())
	                .updatedAt(reply.getUpdatedAt())
	                .build();
	    }
}
