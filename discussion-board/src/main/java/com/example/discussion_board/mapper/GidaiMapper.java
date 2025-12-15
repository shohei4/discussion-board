package com.example.discussion_board.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.discussion_board.dto.GidaiRegistrationRequest;
import com.example.discussion_board.dto.GidaiResponse;
import com.example.discussion_board.dto.GidaiUpdateRequest;
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
				.title(gidai.getTitle())
				.body(gidai.getBody())
				.genre(gidai.getGenre().getLabel())
				.userSummary(userSummary)
				.build();
	}

	/**
	* GidaiRequest -> Gidai(Entity) への変換
	* @param request GidaiRequest
	* @param user    Userエンティティ（関連付け）
	* @return Gidai Entity
	*/
	public Gidai toEntity(GidaiRegistrationRequest request, User user) {
		Gidai gidai = new Gidai();
		gidai.setTitle(request.getTitle());
		gidai.setBody(request.getBody());
		gidai.setGenre(Genre.fromLabel(request.getGenre()));
		gidai.setUser(user);
		return gidai;
	}
	
	/**
	 * id検索で取得した既存の議題に入力項目を上書き
	 * @param gidai
	 * @param request
	 * @return　議題Entity
	 */
	public Gidai updateEntity(Gidai gidai, GidaiUpdateRequest request) {
		gidai.setTitle(request.getTitle());
		gidai.setBody(request.getBody());
		gidai.setGenre(Genre.fromLabel(request.getGenre()));
		gidai.setIsDeleted(request.getIsDeleted());
		return gidai;
	}

	public List<GidaiResponse> toResponseList(List<Gidai> gidaiList) {
		return gidaiList.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

}
