package com.example.discussion_board.secrity;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;

	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		// ここがあなたの書いたJWT解析・認証部分
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			
			String email = jwtTokenProvider.extractEmail(token);
			
			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				//tokenから取得したemailから特定のユーザーを取得
				UserDetails user = userDetailsService.loadUserByUsername(email);
				//tokenと取得したユーザーのemailが同じか判定
				if (jwtTokenProvider.validateToken(token, user.getUsername())) {
					SecurityContextHolder.getContext().setAuthentication(
							new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
				}
			}
		}

		// 🔹 次のフィルターに処理を渡す
		filterChain.doFilter(request, response);

		// ここで特別な処理が必要な場合だけ追加
		// 例：レスポンスヘッダを加工したいときなど
	}
}
