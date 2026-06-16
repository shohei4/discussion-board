package com.example.discussion_board.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscussionItemWithReplyResponse {
	private Long id;
	private String comment;
	private UserSummary userSummary;
	private GidaiSummary gidaiSummary;
	//いいねDTO
	private LikeResultResponse likeResult;
	//編集ボタン表示用のflagフィールド
	private boolean editable;
	private String createdAt;
	private String updatedAt;
	private List<ReplyResponse> replies;
}
