package com.example.discussion_board.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.discussion_board.config.PermitPath;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
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
	                CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

	                // ---------------------------
	                // ⑤ Token とユーザーの照合
	                // ---------------------------
	                
	                //エラー：ここで内部処理に入っていない→引き数にusernameが入っていた
	                if (jwtTokenProvider.validateToken(token, user.getEmail())) {
	                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
	                            user, null, user.getAuthorities());
	                    SecurityContextHolder.getContext().setAuthentication(auth);
	                }
	            }

	        } catch (ExpiredJwtException e) {
	            log.warn("JWT expired: {}", e.getMessage());
	            sendUnauthorized(response, "access_token_expired", "アクセストークンの期限が切れています");
	            return;

	        } catch (SignatureException e) {
	            log.warn("JWT signature invalid: {}", e.getMessage());
	            sendUnauthorized(response, "invalid_signature", "トークンの署名が不正です");
	            return;

	        } catch (MalformedJwtException e) {
	            log.warn("JWT malformed: {}", e.getMessage());
	            sendUnauthorized(response, "malformed_token", "トークンの形式が不正です");
	            return;

	        } catch (UnsupportedJwtException e) {
	            log.warn("JWT unsupported: {}", e.getMessage());
	            sendUnauthorized(response, "unsupported_token", "サポートされていないトークン形式です");
	            return;

	        } catch (IllegalArgumentException e) {
	            log.warn("JWT illegal argument (possibly null): {}", e.getMessage());
	            sendUnauthorized(response, "invalid_token", "トークンが空か不正です");
	            return;

	        } catch (JwtException e) {
	            log.warn("JWT general exception: {}", e.getMessage());
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
