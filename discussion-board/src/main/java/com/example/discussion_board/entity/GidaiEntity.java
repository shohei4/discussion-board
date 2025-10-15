package com.example.discussion_board.entity;

import java.time.LocalDateTime;

import com.example.discussion_board.model.Genre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gidai_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GidaiEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 200)
	private String gidai;
	
	@Enumerated(EnumType.STRING) // Enum を文字列で DB に保存
	@Column(nullable = false)
	private Genre genre;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	//登録時に日時を自動設定
	public void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
