package com.example.discussion_board.mapper;

import org.springframework.stereotype.Component;

import com.example.discussion_board.dto.ReplyRequest;
import com.example.discussion_board.dto.ReplyResponse;
import com.example.discussion_board.dto.UserSummary;
import com.example.discussion_board.entity.DiscussionItem;
import com.example.discussion_board.entity.Reply;
import com.example.discussion_board.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReplyMapper {
	
	
	/**
	 * レスポンスDTOへの変換メソッド
	 * @param reply
	 * @return ReplyResponse
	 */
	 public ReplyResponse toResponse(Reply reply) {
	        User user = reply.getUser();
	        UserSummary userSummary = new UserSummary(user.getId(), user.getUsername());
	        return ReplyResponse.builder()
	                .id(reply.getId())
	                .userSummary(userSummary)
	                .replyComment(reply.getReplyComment())
	                .createdAt(reply.getCreatedAt())
	                .updatedAt(reply.getUpdatedAt())
	                .build();
	    }
	 
	 /**
	  * Entityへの変換メソッド
	  * @param request
	  * @param user
	  * @param commentId
	  * @return Reply
	  */
	 public Reply toEntity(ReplyRequest request,User user,DiscussionItem discussionItem) {
		 return Reply.builder()
				 .discussionItem(discussionItem)
				 .replyComment(request.getReplyComment())
				 .user(user)
				 .build();
	 }
}
