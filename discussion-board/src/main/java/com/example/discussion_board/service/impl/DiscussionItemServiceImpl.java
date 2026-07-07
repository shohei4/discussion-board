package com.example.discussion_board.service.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.DiscussionItemRequest;
import com.example.discussion_board.dto.DiscussionItemResponse;
import com.example.discussion_board.dto.DiscussionItemWithReplyResponse;
import com.example.discussion_board.dto.GidaiSummary;
import com.example.discussion_board.dto.LikeResultResponse;
import com.example.discussion_board.dto.ReplyResponse;
import com.example.discussion_board.dto.UserSummary;
import com.example.discussion_board.entity.DiscussionItem;
import com.example.discussion_board.entity.Gidai;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.mapper.DiscussionItemMapper;
import com.example.discussion_board.mapper.ReplyMapper;
import com.example.discussion_board.repository.DiscussionItemRepository;
import com.example.discussion_board.repository.GidaiRepository;
import com.example.discussion_board.security.CustomUserDetails;
import com.example.discussion_board.service.CommentLikeService;
import com.example.discussion_board.service.CurrentUserService;
import com.example.discussion_board.service.DiscussionItemService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscussionItemServiceImpl implements DiscussionItemService {
	
	private final DiscussionItemRepository discussionItemRepository;
	private final GidaiRepository gidaiRepository;
	private final DiscussionItemMapper discussionItemMapper;
	private final CommentLikeService commentLikeService;
	private final ReplyMapper replyMapper;
	private final CurrentUserService currentUserService;
	
	@Override
	public List<DiscussionItemWithReplyResponse> findAllByGidaiId(Long gidaiId) {
		// TODO 自動生成されたメソッド・スタブ
		
		//議論コメントレスポンスのビルド
		List<DiscussionItemWithReplyResponse> responses = 
				discussionItemRepository.findAllWithRepliesByGidaiId(gidaiId)
					.stream()
					.map((DiscussionItem item) -> {
						//編集flag状態を取得
						Authentication auth = SecurityContextHolder.getContext().getAuthentication();
						boolean editable = currentUserService.isOwner(item.getUser().getId());
						
						//いいねカウントの取得
						Long likeCount = commentLikeService.conuntLikes(item.getId());
						//いいね状態の取得
						//Authenticationから現在のlog inユーザの詳細を取得
						CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
						Boolean isLiked = commentLikeService.getIsLiked(item.getId(), userDetails.getId());
						LikeResultResponse likeResult = new LikeResultResponse(likeCount, isLiked);
						
						List<ReplyResponse> replies = item.getReplies()
						        .stream()
						        .map(reply -> {
						            ReplyResponse response = replyMapper.toResponse(reply); // ①の変換
						            response.setEditable(currentUserService.isOwner(reply.getUser().getId())); // ②のeditable設定
						            return response;
						        })
						        .toList();
						
						
						DiscussionItemWithReplyResponse response =
						        DiscussionItemWithReplyResponse.builder()
						            .id(item.getId())
						            .comment(item.getComment())
						            .userSummary(UserSummary.from(item.getUser()))
						            .gidaiSummary(GidaiSummary.from(item.getGidai()))
						            .createdAt(item.getCreatedAt())
						            .updatedAt(item.getUpdatedAt())
						            .replies(replies)
						            .likeResult(likeResult)
						            .editable(editable)
						            .build();
						return response;
					})
					.toList();
		return responses;
	}
	
	@Override
	public DiscussionItemResponse findById(Long discussionItemId) {
		// TODO 自動生成されたメソッド・スタブ
		DiscussionItem discussionItem = discussionItemRepository.findById(discussionItemId)
				.orElseThrow(() -> new EntityNotFoundException("議論コメントが見つかりません"));
		
		DiscussionItemResponse response = discussionItemMapper.toResponse(discussionItem);
		return response;
	}

	
	@Override
	public DiscussionItemResponse createDiscussionItem(DiscussionItemRequest request, Long gidaiId, User user) {
		// TODO 自動生成されたメソッド・スタブ
		Gidai gidai = gidaiRepository.findById(gidaiId)
				.orElseThrow(() -> new IllegalArgumentException(
		                "指定された議題IDが存在しません: " + gidaiId));
		DiscussionItem discussionItem = DiscussionItem.builder()
		.comment(request.getComment())
		.gidai(gidai)
		.user(user)
		.build();
		discussionItemRepository.save(discussionItem);
		//いいねカウントの取得
		Long likeCount = commentLikeService.conuntLikes(discussionItem.getId());
		//いいね状態の取得
		boolean isLiked = commentLikeService.getIsLiked(discussionItem.getId(), user.getId());
		LikeResultResponse likeResult = new LikeResultResponse(likeCount, isLiked);
		DiscussionItemResponse response = discussionItemMapper.toResponse(discussionItem, likeResult, true);
		return response;
	}

	@Override
	public DiscussionItemResponse editDiscussionItem(DiscussionItemRequest request, Long discussionItemId) {
		// TODO 自動生成されたメソッド・スタブ
		DiscussionItem item = discussionItemRepository.findById(discussionItemId)
	            .orElseThrow(() -> new EntityNotFoundException("議事項目が見つかりません: " + discussionItemId));
		//フィールド更新
		item.setComment(request.getComment());
		
		//更新method
		//saveAndFlushを使う理由は@PreUpdateをこの時点で効かせるため
		DiscussionItem saved = discussionItemRepository.saveAndFlush(item);
		
		//DTOに変換
		DiscussionItemResponse response = discussionItemMapper.toResponse(saved);
		return response;
	}

	@Override
	public void deleteDiscussionItem(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
