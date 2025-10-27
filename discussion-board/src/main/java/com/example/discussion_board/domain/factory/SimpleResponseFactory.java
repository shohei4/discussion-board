package com.example.discussion_board.domain.factory;

import com.example.discussion_board.domain.dto.GidaiResponse;
import com.example.discussion_board.domain.dto.UserResponse;
import com.example.discussion_board.domain.entity.Gidai;

public class SimpleResponseFactory extends BaseResponseFactory {

	@Override
	public GidaiResponse createGidaiResponse(Gidai gidai) {
		return GidaiResponse.builder()
				.id(gidai.getId())
				.gidai(gidai.getGidai())
				.genre(gidai.getGidai())
				.user(UserResponse.builder()
		                .id(gidai.getUser().getId())
		                .username(gidai.getUser().getUsername())
		                .build())
		        .build();
				
	}

}
