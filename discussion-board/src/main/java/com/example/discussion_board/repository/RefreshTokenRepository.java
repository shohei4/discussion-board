package com.example.discussion_board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.discussion_board.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByTokenHash(String tokenHash);
	List<RefreshToken> findAllByUserId(Long userId);
	void deleteAllByUserId(Long userId);
}
 