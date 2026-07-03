package com.example.discussion_board.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.discussion_board.model.Genre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gidai_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gidai {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false, unique = true, length = 100)
	private String title;
	
	@Column(nullable = false, unique = true, length = 1000)
	private String body;

	@Enumerated(EnumType.STRING) // Enum を文字列で DB に保存
	@Column(nullable = false)
	private Genre genre;
	
	@Column(name = "is_deleted")
	@Builder.Default
	private Boolean isDeleted = false;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column  // @UpdateTimestamp を削除 フロントでupdateATの有無で表示を切り替えるため
	private LocalDateTime updatedAt;

	@PreUpdate
	public void onUpdate() {
	    this.updatedAt = LocalDateTime.now();
	}
}
