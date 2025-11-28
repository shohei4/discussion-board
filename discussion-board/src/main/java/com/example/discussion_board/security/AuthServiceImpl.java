package com.example.discussion_board.security;

import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.discussion_board.repository.RefreshTokenRepository;
import com.example.discussion_board.repository.UserRepository;

import com.example.discussion_board.entity.User;
import com.example.discussion_board.entity.RefreshToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public LoginResponse login(String email, String password) {
		// TODO 自動生成されたメソッド・スタブ
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		if(!passwordEncoder.matches(password, user.getPassword()) ) {
			throw new BadCredentialsEcxception("Invalid password");
		}
		return null;
	}

	@Override
	public RefreshTokenResponse refeshAccessToken(String refreshToken) {
		// TODO 自動生成されたメソッド・スタブ
		String plainToken = UUID.randomUUID().toString();//ランダム文字列
		String tokenHash = hashToken(plainToken);
		
		RefreshToken refreshToken = RefreshToken.builder()
				.userId(user.getId())
		return null;
	}

	@Override
	public void logout(String refreshToken) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
