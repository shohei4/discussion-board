package com.example.discussion_board.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.discussion_board.dto.UserRequest;
import com.example.discussion_board.dto.UserResponse;
import com.example.discussion_board.dto.UserSummary;
import com.example.discussion_board.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {
	
	private final ModelMapper modelMapper;
	 	
	public UserResponse toResponse(User user) {
		 return modelMapper.map(user, UserResponse.class);
	}
	
	public List<UserResponse> toResponseList(List<User> users) {
		return  users.stream()            //Streamに変換
					.map(this::toResponse)//かくUserをUserResponse型に変換
					.collect(Collectors.toList());//Listに戻す
	}
	
	public User toEntity(UserRequest request) {
		return modelMapper.map(request, User.class);
	}
	
	// User Entity → UserSummary DTO（GidaiResponse 用）
    public UserSummary toSummary(User user) {
        UserSummary summary = new UserSummary(user.getId(),user.getUsername());
        return summary;
    }
	

}
