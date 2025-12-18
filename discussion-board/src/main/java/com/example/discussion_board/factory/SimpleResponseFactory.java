package com.example.discussion_board.factory;

import com.example.discussion_board.dto.GidaiResponse;
import com.example.discussion_board.dto.UserSummary;
import com.example.discussion_board.entity.Gidai;

public class SimpleResponseFactory extends BaseResponseFactory {

	@Override
	public GidaiResponse createGidaiResponse(Gidai gidai) {
		return GidaiResponse.builder()
				.id(gidai.getId())
				.gidai(gidai.getGidai())
				.genre(gidai.getGidai())
				.userSummary(new UserSummary(gidai.getUser().getId(), gidai.getUser().getUsername()))
		        .build();
				
	}

}
