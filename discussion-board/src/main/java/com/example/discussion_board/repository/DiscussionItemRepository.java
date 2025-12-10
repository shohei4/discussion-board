package com.example.discussion_board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.discussion_board.entity.DiscussionItem;

@Repository
public interface DiscussionItemRepository extends JpaRepository<DiscussionItem, Long>{
	public List<DiscussionItem> findAllByGidaiId(Long gidaiId);
}
