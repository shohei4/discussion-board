package com.example.discussion_board.util;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final String SECRET_KEY = "secret123secret123secret123secret12";
	private final SecretKey key;
	
	public JwtUtil() {
		//文字列からSecretKeyを生成
		this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}

	public String genereteToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String  extractUsername(String token) {
		JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        return parser.parseClaimsJws(token)
                     .getBody()
                     .getSubject();
	}
	
	public boolean validateToken(String token, String username) {
		return  extractUsername(token).equals(username) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        return parser.parseClaimsJws(token)
                     .getBody()
                     .getExpiration()
                     .before(new Date());
	}
}
