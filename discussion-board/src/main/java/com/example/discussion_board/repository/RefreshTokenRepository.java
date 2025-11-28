package com.example.discussion_board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.discussion_board.entity.RefleshToken;

public interface RefreshTokenRepository extends JpaRepository<RefleshToken, Long> {
	Optional<RefleshToken> findByTokenHash(String tokenHash);
	List<RefleshToken> findAllByUserId(Long userId);
	void deleteAllByUserId(Long userId);
}
