package com.example.discussion_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.discussion_board.entity.ReplyLike;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long>{
	
	boolean existsByReply_IdAndUser_Id(Long replyId, Long userId);
	
	Long countByReply_Id(Long replyId);
	
	void deleteByReply_IdAndUser_Id(Long replyId, Long commentId);
}
