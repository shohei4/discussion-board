package com.example.discussion_board.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.discussion_board.entity.Gidai;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.model.Genre;

@Repository
public interface GidaiRepository extends JpaRepository<Gidai, Long> {
	List<Gidai> findAllByIsDeletedFalse();
	Optional<Gidai> findByIdAndIsDeletedFalse(Long Id);
	List<Gidai> findByGenreAndIsDeletedFalse(Genre Genre);
	List<Gidai> findByUserAndIsDeletedFalse(User user);
}
 