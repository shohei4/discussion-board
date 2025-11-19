package com.example.discussion_board.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
		
		//フィルタ除外処理
		String path = request.getRequestURI();
		// API は equals（完全一致）
		if (path.equals("/api/auth/login")
		        || path.equals("/api/users")) {

		    filterChain.doFilter(request, response);
		    return;
		}
		
		// view は startsWith（前方一致）
		if (path.startsWith("/login")
		        || path.startsWith("/users/registration")) {

		    filterChain.doFilter(request, response);
		    return;
		}
		

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

		// 🔹 次のフィルターに処理を渡す(SpringSecurityの標準フィルター)
		filterChain.doFilter(request, response);

		// ここで特別な処理が必要な場合だけ追加
		// 例：レスポンスヘッダを加工したいときなど
	}
}
