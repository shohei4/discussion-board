package com.example.discussion_board.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "refleshToken")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id, nullable = false")
	private User user;
	
	private String tokenHash;
	private Instant createdAt;
	/** トークンの有効期限*/
	private Instant expiresAt;
	/** トークンが既に使われたかどうか*/
	private boolean used;
	/** トークンが強制的に無効化されているか*/
	private boolean revoked;
	/** トークンの世代番号(複数トークンの順序管理)*/
	private int tokenVersion;

}
