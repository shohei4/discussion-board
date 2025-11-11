package com.example.discussion_board.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.UserRequest;
import com.example.discussion_board.dto.UserResponse;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.mapper.UserMapper;
import com.example.discussion_board.repository.UserRepository;
import com.example.discussion_board.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	/** 全件取得 */
	public List<UserResponse> findAllUser() {
		List<User> users = userRepository.findAll();
		return userMapper.toResponseList(users);
	}

	@Override
	/** ID検索(外部処理用) */
	public UserResponse findByIdUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("指定のユーザーが存在しません"));
		return userMapper.toResponse(user);
	}
	
	public User findByIdUserEntity(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("指定のユーザーが存在しません"));
		return user;
	}
	
	@Override
	public User getUserEntityById(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
		user.setUsername(user.getUsername());
		//パスワードハッシュ化
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
		return userMapper.toResponse(user);
	}

	@Override
	/** 更新 */
	public UserResponse editUser(UserRequest request) {
		User existingUser = userRepository.findById(request.getId())
				.orElseThrow(() -> new NoSuchElementException("指定のユーザーが存在しません"));

		// 更新可能なフィールドだけ反映
		existingUser.setUsername(request.getUsername());
		existingUser.setEmail(request.getEmail());
		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			existingUser.setPassword(request.getPassword());
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
