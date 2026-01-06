package com.example.discussion_board.service.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.DiscussionItemRequest;
import com.example.discussion_board.dto.DiscussionItemResponse;
import com.example.discussion_board.dto.LikeResultResponse;
import com.example.discussion_board.entity.DiscussionItem;
import com.example.discussion_board.entity.Gidai;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.mapper.DiscussionItemMapper;
import com.example.discussion_board.repository.DiscussionItemRepository;
import com.example.discussion_board.repository.GidaiRepository;
import com.example.discussion_board.repository.UserRepository;
import com.example.discussion_board.service.CommentLikeService;
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
	private final CommentLikeService commentLikeService;
	
	@Override
	public List<DiscussionItemResponse> findAllByGidaiId(Long gidaiId) {
		// TODO 自動生成されたメソッド・スタブ
		
		//議論コメントレスポンスのビルド
		List<DiscussionItemResponse> responses = 
				discussionItemRepository.findAllByGidaiId(gidaiId)
					.stream()
					.map((DiscussionItem item) -> {
						//いいねカウントの取得
						Long likeCount = commentLikeService.conuntLikes(item.getId());
						//いいね状態の取得
						Boolean isLiked = commentLikeService.getIsLiked(item.getId(), item.getUser().getId());
						LikeResultResponse likeResult = new LikeResultResponse(likeCount, isLiked);
						
						//編集flag状態を取得
						Authentication auth = SecurityContextHolder.getContext().getAuthentication();
						String  loginUsername = auth.getName();
						boolean editable = item.getUser().getUsername().equals(loginUsername);
						
						DiscussionItemResponse response = DiscussionItemResponse.from(item)								
								.likeResult(likeResult)
								.editable(editable)
								.build();
						return response;
					})
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
		boolean isLiked = commentLikeService.getIsLiked(discussionItem.getId(), userId);
		LikeResultResponse likeResult = new LikeResultResponse(likeCount, isLiked);
		DiscussionItemResponse response = discussionItemMapper.toResponse(discussionItem, likeResult);
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
