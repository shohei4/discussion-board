package com.example.discussion_board.dto;

import com.example.discussion_board.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummary {
	private Long id;
	private String username;
	
	public static UserSummary from(User user) {
        return new UserSummary(
            user.getId(),
            user.getUsername()
        );
    }
}
