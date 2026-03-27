package com.example.discussion_board.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 30)
	@NotBlank(message = "ユーザー名は必須です")
	@Size(min = 3, max = 30, message = "ユーザー名は３から３０文字以内で入力してください")
	private String username;

	@Column(nullable = false, unique = true, length = 254)
	@NotBlank(message = "メールアドレスは必須です")
	@Email(message = "正しいメールアドレスを入力してください")
	private String email;

	@Column(nullable = false, length = 255)
	@NotBlank(message = "パスワードは必須です")
	@Size(min = 8, max = 100, message = "パスワードは8文字以上で入力してください")
	private String password;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
