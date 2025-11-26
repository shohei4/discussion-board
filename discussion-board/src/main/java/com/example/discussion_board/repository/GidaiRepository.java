package com.example.discussion_board.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.discussion_board.entity.Gidai;

@Repository
public interface GidaiRepository extends JpaRepository<Gidai, Long> {	
	
}
