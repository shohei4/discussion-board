package com.example.discussion_board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.discussion_board.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{
	List<Reply> findByICommentIdAndsDeletedFalse(Long commentId);
	
}
