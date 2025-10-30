package com.example.discussion_board.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.discussion_board.domain.dto.UserRequest;
import com.example.discussion_board.domain.dto.UserResponse;
import com.example.discussion_board.domain.entity.User;

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
	

}
