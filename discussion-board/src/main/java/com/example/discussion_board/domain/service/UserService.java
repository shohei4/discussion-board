package com.example.discussion_board.domain.service;

import java.util.List;

import com.example.discussion_board.domain.entity.User;

public interface UserService {
	
	/**
	 * 全件取得
	 * @return　User型List
	 */
	List<User> findAllUser();
	
	/**
	 * Id検索
	 * @return User型のオブジェクト
	 */
	User findByIdUser(Long id);
	
	/**
	 * ユーザー名検索
	 * @return User型のオブジェクト
	 */
	User findByUsernameUser(String username);
	
	/**
	 * ユーザー情報を登録
	 * @param  user
	 */
	User createUser(User user);
	
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
