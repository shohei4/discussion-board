package com.example.discussion_board.dto;

import com.example.discussion_board.entity.DiscussionItem;

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
public class DiscussionItemResponse {

	private Long id;
	private String comment;
	private UserSummary userSummary;
	private GidaiSummary gidaiSummary;
	private long likeCount;
	//いいねボタン表示用のflagフィールド
	private boolean isLiked;
	//編集ボタン表示用のflagフィールド
	private boolean editable;
	private String createdAt;
	private String updatedAt;
	
	//議論コメントエンティティの基本フィールドをビルドするformメソッド
	public static DiscussionItemResponseBuilder from(DiscussionItem item) {
        return DiscussionItemResponse.builder()
            .id(item.getId())
            .comment(item.getComment())
            .userSummary(UserSummary.from(item.getUser()))
            .gidaiSummary(GidaiSummary.from(item.getGidai()))
            .createdAt(item.getCreatedAt().toString())
            .updatedAt(item.getUpdatedAt().toString());
    }
}
