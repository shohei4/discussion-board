package com.example.discussion_board.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.discussion_board.dto.GidaiRequest;
import com.example.discussion_board.dto.GidaiResponse;
import com.example.discussion_board.entity.Gidai;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.model.Genre;
import com.example.discussion_board.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GidaiMapper {
	
	private final ModelMapper modelMapper;
	private final UserService userService;
	private final UserMapper userMapper;
	/**
	 * 議題レスポンスDTOへの変換
	 * @param gidai
	 * @return GidaiResponseDTO
	 */
	public GidaiResponse toResponse(Gidai gidai) {
		return modelMapper.map(gidai, GidaiResponse.class);
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
