package com.example.discussion_board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.discussion_board.entity.DiscussionItem;

@Repository
public interface DiscussionItemRepository extends JpaRepository<DiscussionItem, Long>{
	public List<DiscussionItem> findAllByGidaiId(Long gidaiId);
	
	//repliesも一括取得する
	@Query("SELECT DISTINCT d FROM DiscussionItem d LEFT JOIN FETCH d.replies WHERE d.gidai.id = :gidaiId")
    List<DiscussionItem> findAllWithRepliesByGidaiId(@Param("gidaiId") Long gidaiId);
}
