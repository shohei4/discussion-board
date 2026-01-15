package com.example.discussion_board.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.discussion_board.entity.Gidai;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.model.Genre;

@Repository
public interface GidaiRepository extends JpaRepository<Gidai, Long> {
	@Query("SELECT g FROM Gidai g WHERE " +
	           "g.isDeleted = false AND " + // 削除されていないものに限定
	           "(:genre IS NULL OR g.genre = :genre) AND " +
	           "(:keyword IS NULL OR g.title LIKE %:keyword%)")
	    List<Gidai> searchGidai(
	        @Param("genre") Genre genre, 
	        @Param("keyword") String keyword
	    );
	
	List<Gidai> findAllByIsDeletedFalse();
	Optional<Gidai> findByIdAndIsDeletedFalse(Long Id);
	List<Gidai> findByGenreAndIsDeletedFalse(Genre Genre);
	List<Gidai> findByUserAndIsDeletedFalse(User user);
}
 