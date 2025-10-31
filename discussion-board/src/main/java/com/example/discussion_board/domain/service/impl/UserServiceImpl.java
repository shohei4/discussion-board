package com.example.discussion_board.domain.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.domain.entity.User;
import com.example.discussion_board.domain.repository.UserRepository;
import com.example.discussion_board.domain.service.UserService;
import com.example.discussion_board.dto.UserRequest;
import com.example.discussion_board.dto.UserResponse;
import com.example.discussion_board.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	/** 全件取得 */
	public List<UserResponse> findAllUser() {
		List<User> users = userRepository.findAll();
		return userMapper.toResponseList(users);
	}

	@Override
	/** ID検索 */
	public UserResponse findByIdUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("指定のユーザーが存在しません"));
		return userMapper.toResponse(user);
	}

	@Override
	/** ユーザー名検索 */
	public UserResponse findByUsernameUser(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NoSuchElementException("指定のユーザーが存在しません"));
		;
		return userMapper.toResponse(user);
	}

	@Override
	/** 登録 */
	public UserResponse createUser(UserRequest request) {
		User user = userMapper.toEntity(request);
		User saved = userRepository.save(user);
		return userMapper.toResponse(saved);
	}

	@Override
	/** 更新 */
	public UserResponse editUser(UserRequest request) {
		User existingUser = userRepository.findById(request.getId())
				.orElseThrow(() -> new NoSuchElementException("指定のユーザーが存在しません"));

		// 更新可能なフィールドだけ反映
		existingUser.setUsername(request.getUsername());
		existingUser.setEmail(request.getEmail());
		if (request.getPassowrd() != null && !request.getPassowrd().isBlank()) {
			existingUser.setPassword(request.getPassowrd());
		}

		return userMapper.toResponse(existingUser);
	}

	@Override
	/** 削除 */
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("指定のユーザーが存在しません"));
		userRepository.delete(user);
	}

}
