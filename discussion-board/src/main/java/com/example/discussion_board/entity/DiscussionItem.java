package com.example.discussion_board.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discussion_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscussionItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String comment;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gidai_id", nullable = false)
	private Gidai gidai;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_Id", nullable = false)
	private User user;
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	@Column
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	public void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
