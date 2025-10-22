package com.example.discussion_board.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.discussion_board.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	//ユーザー名検索のカスタムメソッド
	Optional<User> findByUsername(String username);
}
