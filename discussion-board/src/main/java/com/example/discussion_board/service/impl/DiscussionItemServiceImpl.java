package com.example.discussion_board.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.DiscussionItemRequest;
import com.example.discussion_board.dto.DiscussionItemResponse;
import com.example.discussion_board.dto.DiscussionItemWithReplyResponse;
import com.example.discussion_board.dto.LikeResultResponse;
import com.example.discussion_board.dto.ReplyResponse;
import com.example.discussion_board.entity.DiscussionItem;
import com.example.discussion_board.entity.Gidai;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.mapper.DiscussionItemMapper;
import com.example.discussion_board.mapper.ReplyMapper;
import com.example.discussion_board.repository.DiscussionItemRepository;
import com.example.discussion_board.repository.GidaiRepository;
import com.example.discussion_board.service.CurrentUserService;
import com.example.discussion_board.service.DiscussionItemService;
import com.example.discussion_board.service.LikeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscussionItemServiceImpl implements DiscussionItemService {
	
	private final DiscussionItemRepository discussionItemRepository;
	private final GidaiRepository gidaiRepository;
	private final DiscussionItemMapper discussionItemMapper;
	private final LikeService commentLikeService;
	private final LikeService replyLikeService;
	private final ReplyMapper replyMapper;
	private final CurrentUserService currentUserService;
	
	@Override
	public List<DiscussionItemWithReplyResponse> findAllByGidaiId(Long gidaiId, Long userId) {
		// TODO 自動生成されたメソッド・スタブ
		
		//議論コメントレスポンスのビルド
		List<DiscussionItemWithReplyResponse> responses = 
				discussionItemRepository.findAllWithRepliesByGidaiId(gidaiId)
					.stream()
					.map((DiscussionItem item) -> {
						//編集flag状態を取得
						boolean editable = currentUserService.isOwner(item.getUser().getId());
						
						//いいねカウントの取得
						Long likeCount = commentLikeService.conuntLikes(item.getId());
						//いいね状態の取得
						Boolean isLiked = commentLikeService.getIsLiked(item.getId(), userId);
						LikeResultResponse likeResult = new LikeResultResponse(likeCount, isLiked);
						
						List<ReplyResponse> replies = item.getReplies()
						        .stream()
						        .map(reply -> {
						            ReplyResponse response = replyMapper.toResponse(reply); // ①の変換
						            response.setEditable(currentUserService.isOwner(reply.getUser().getId())); // ②のeditable設定
						            //いいね情報の取得
						            Long replyLikeCount = replyLikeService.conuntLikes(reply.getId());
						            Boolean replyIsLiked = replyLikeService.getIsLiked(reply.getId(), userId);
						            LikeResultResponse replyLikeResult = new LikeResultResponse(replyLikeCount, replyIsLiked);
						            response.setLikeResult(replyLikeResult);
						            return response;
						        })
						        .toList();
						
						return discussionItemMapper.toResponseWithReplies(item, replies, likeResult, editable);
					})
					.toList();
		return responses;
	}
	
	@Override
	public DiscussionItemWithReplyResponse findById(Long discussionItemId, Long userId) {
		// TODO 自動生成されたメソッド・スタブ
		DiscussionItem item = discussionItemRepository.findById(discussionItemId)
				.orElseThrow(() -> new EntityNotFoundException("議論コメントが見つかりません"));
		
		Boolean editable = currentUserService.isOwner(item.getUser().getId());
		//いいねカウントの取得
		Long likeCount = commentLikeService.conuntLikes(item.getId());
		//いいね状態の取得
		Boolean isLiked = commentLikeService.getIsLiked(item.getId(), userId);
		LikeResultResponse likeResult = new LikeResultResponse(likeCount, isLiked);
		
		
		//返信情報取得
		List<ReplyResponse> replies = item.getReplies()
		        .stream()
		        .map(reply -> {
		            ReplyResponse replyResponse = replyMapper.toResponse(reply); // ①の変換
		            replyResponse.setEditable(currentUserService.isOwner(reply.getUser().getId())); // ②のeditable設定
		            //いいね情報の取得
		            Long replyLikeCount = replyLikeService.conuntLikes(reply.getId());
		            Boolean replyIsLiked = replyLikeService.getIsLiked(reply.getId(), userId);
		            LikeResultResponse replyLikeResult = new LikeResultResponse(replyLikeCount, replyIsLiked);
		            replyResponse.setLikeResult(replyLikeResult);
		            return replyResponse;
		        })
		        .toList();
		
		return discussionItemMapper.toResponseWithReplies(item, replies, likeResult, editable);
	}

	
	@Override
	public DiscussionItemResponse createDiscussionItem(DiscussionItemRequest request, Long gidaiId, User user) {
		// TODO 自動生成されたメソッド・スタブ
		Gidai gidai = gidaiRepository.findById(gidaiId)
				.orElseThrow(() -> new IllegalArgumentException(
		                "指定された議題IDが存在しません: " + gidaiId));
		DiscussionItem item = DiscussionItem.builder()
		.comment(request.getComment())
		.gidai(gidai)
		.user(user)
		.build();
		discussionItemRepository.save(item);
		
		//いいねカウントの取得
		Long likeCount = commentLikeService.conuntLikes(item.getId());
		//いいね状態の取得
		boolean isLiked = commentLikeService.getIsLiked(item.getId(), user.getId());
		LikeResultResponse likeResult = new LikeResultResponse(likeCount, isLiked);
		DiscussionItemResponse response = discussionItemMapper.toResponse(item, likeResult, true);
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
		response.setEditable(true);
		return response;
	}

	@Override
	public void deleteDiscussionItem(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
