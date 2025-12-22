package com.example.discussion_board.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
	/**
     * ユーザー名からUserDetailsを取得（Spring Securityでログイン時に使用）
     * @param username ログインフォームから送信されたユーザー名
     * @return UserDetailsオブジェクト
     * @throws UsernameNotFoundException 指定ユーザーが存在しない場合
     */
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    
}
