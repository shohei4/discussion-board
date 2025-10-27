package com.example.discussion_board.domain.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.domain.dto.UserResponse;
import com.example.discussion_board.domain.entity.User;
import com.example.discussion_board.domain.repository.UserRepository;
import com.example.discussion_board.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	//DI
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public List<UserResponse> findAllUser() {
		// TODO 自動生成されたメソッド・スタブ
		List<User> users = userRepository.findAll();
		return users.stream()
				.map(user -> modelMapper.map(user, UserResponse.class))
				.collect(Collectors.toList());		
	}

	@Override
	public User findByIdUser(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
	}

	@Override
	public User findByUsernameUser(String username) {
		// TODO 自動生成されたメソッド・スタブ
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("ユーザー名" + username + "が見つかりません"));
	}

	@Override
	public User createUser(User user) {
		// TODO 自動生成されたメソッド・スタブ
		return userRepository.save(user);
	}

	@Override
	public User editUser(User user) {
		// TODO 自動生成されたメソッド・スタブ
		//更新前にユーザー情報がデータベースにあるか確認
		User existing = findByIdUser(user.getId());
		existing.setUsername(user.getUsername());
		existing.setEmail(user.getEmail());
		
		return userRepository.save(existing);
	}

	@Override
	public void deleteUser(User user) {
		// TODO 自動生成されたメソッド・スタブ
		userRepository.delete(user);
	}

}
