package com.example.discussion_board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.discussion_board.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	//ユーザー名検索のカスタムメソッド
	Optional<User> findByUsername(String username);
	
	//Emailで一意のユーザーを検索
	Optional<User> findByEmail(String email);
}
