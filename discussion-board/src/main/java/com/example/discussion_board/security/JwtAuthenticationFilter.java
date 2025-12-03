package com.example.discussion_board.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.discussion_board.config.PermitPath;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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

	 @Override
	    protected void doFilterInternal(
	            HttpServletRequest request,
	            HttpServletResponse response,
	            FilterChain filterChain) throws ServletException, IOException {
		 
	        // ---------------------------
	        // ① フィルタ除外 (軽量化)
	        // ---------------------------
	        String path = request.getServletPath();
	        String method = request.getMethod();
	        //permitAllのバスはスキップ
	        boolean excluded = PermitPath.defaultPermitPaths()
	        		.stream()
	        		.anyMatch(p -> p.matches(path, method));
	        if (excluded) {
	            filterChain.doFilter(request, response);
	            return;
	        }

	        // ---------------------------
	        // ② Authorization ヘッダから token 取得
	        // ---------------------------
	        String header = request.getHeader("Authorization");
	        if (header == null || !header.startsWith("Bearer ")) {
	            sendUnauthorized(response, "missing_token", "アクセストークンがありません");
	            return;
	        }

	        String token = header.substring(7);

	        try {
	            // ---------------------------
	            // ③ Token の署名と期限を検証
	            // ---------------------------
	            String email = jwtTokenProvider.extractEmail(token);
	            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	                // ---------------------------
	                // ④ UserDetails を取得
	                // ---------------------------
	                UserDetails user = userDetailsService.loadUserByUsername(email);

	                // ---------------------------
	                // ⑤ Token とユーザーの照合
	                // ---------------------------
	                if (jwtTokenProvider.validateToken(token, user.getUsername())) {
	                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
	                            user, null, user.getAuthorities());
	                    SecurityContextHolder.getContext().setAuthentication(auth);
	                }
	            }

	        } catch (ExpiredJwtException e) {
	            sendUnauthorized(response, "access_token_expired", "アクセストークンの期限が切れています");
	            return;

	        } catch (JwtException e) {
	            sendUnauthorized(response, "invalid_token", "トークンが不正です");
	            return;
	        }

	        // ---------------------------
	        // ⑥ 次のフィルタへ
	        // ---------------------------
	        filterChain.doFilter(request, response);
	    }

	    // ---------------------------
	    // 共通 401 返却メソッド
	    // ---------------------------
	    private void sendUnauthorized(HttpServletResponse response, String error, String message) throws IOException {
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json;charset=UTF-8");
	        response.getWriter().write(String.format("{\"error\":\"%s\",\"message\":\"%s\"}", error, message));
	    }
}
