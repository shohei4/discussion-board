package com.example.discussion_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.discussion_board.entity.CommentLike;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>{
	
	boolean existsByDiscussionItem_IdAndUser_Id(Long commentId, Long userId);
	
	long countByDiscussionItem_Id(Long commentId);
	
	void deleteByDiscussionItem_IdAndUser_Id(Long commentId, Long userId);
}
