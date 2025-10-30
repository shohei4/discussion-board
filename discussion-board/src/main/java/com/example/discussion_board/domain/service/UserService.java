package com.example.discussion_board.domain.service;

import java.util.List;

import com.example.discussion_board.domain.dto.UserRequest;
import com.example.discussion_board.domain.dto.UserResponse;

public interface UserService {

	/**
	 * 全件取得
	 * @return UserResponse型のList
	 */
	List<UserResponse> findAllUser();

	/**
	 * Id検索
	 * @param id ユーザーID
	 * @return UserResponse型
	 */
	UserResponse findByIdUser(Long id);

	/**
	 * ユーザー名検索
	 * @param username ユーザー名
	 * @return UserResponse型
	 */
	UserResponse findByUsernameUser(String username);

	/**
	 * ユーザー情報を登録
	 * @param request 登録用DTO（UserRequestなど）
	 * @return 登録後のUserResponse
	 */
	UserResponse createUser(UserRequest request);

	/**
	 * ユーザー情報を修正
	 * @param request 更新用DTO（UserRequestなど）
	 * @return 更新後のUserResponse
	 */
	UserResponse editUser(UserRequest request);

	/**
	 * ユーザー情報を削除
	 * @param id 削除対象ユーザーID
	 */
	void deleteUser(Long id);
}
