package com.example.discussion_board.domain.service;

import java.util.List;

import com.example.discussion_board.domain.dto.UserResponse;
import com.example.discussion_board.domain.entity.User;

public interface UserService {
	
	/**
	 * 全件取得
	 * @return　User型List
	 */
	List<UserResponse> findAllUser();
	
	/**
	 * Id検索
	 * @return User型のオブジェクト
	 */
	UserResponse findByIdUser(Long id);
	
	/**
	 * ユーザー名検索
	 * @return User型のオブジェクト
	 */
	UserResponse findByUsernameUser(String username);
	
	/**
	 * ユーザー情報を登録
	 * @param  user
	 */
	UserResponse createUser(User user);
	
	/**
	 * ユーザー情報を修正
	 * @param user
	 */
	User editUser(User user);
	
	/**
	 * ユーザー情報を削除
	 * @param user
	 */
	void deleteUser(User user);
	
}
