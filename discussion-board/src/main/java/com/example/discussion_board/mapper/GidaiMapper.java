package com.example.discussion_board.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.discussion_board.dto.GidaiRequest;
import com.example.discussion_board.dto.GidaiResponse;
import com.example.discussion_board.dto.UserSummary;
import com.example.discussion_board.entity.Gidai;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.model.Genre;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GidaiMapper {
	
	/**
	 * 議題レスポンスDTOへの変換
	 * genreをラベルで取得するために手動で記述
	 * @param gidai
	 * @return GidaiResponseDTO
	 */
	public GidaiResponse toResponse(Gidai gidai) {
		User user = gidai.getUser();
		UserSummary userSummary = new UserSummary(user.getId(), user.getUsername());
		return GidaiResponse.builder()
				.id(gidai.getId())
				.gidai(gidai.getGidai())
				.genre(gidai.getGenre().getLabel())
				.userSummary(userSummary)
				.build();
	}
	
	public List<GidaiResponse> toResponseList(List<Gidai> gidaiList) {
		return gidaiList.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}
	

    /**
     * GidaiRequest -> Gidai(Entity) への変換
     * @param request GidaiRequest
     * @param user    Userエンティティ（関連付け）
     * @return Gidai Entity
     */
    public Gidai toEntity(GidaiRequest request, User user) {
        Gidai gidai = new Gidai();
        gidai.setGidai(request.getGidai());
        gidai.setGenre(Genre.fromLabel(request.getGenre()));
        gidai.setUser(user);
        return gidai;
    }
}
