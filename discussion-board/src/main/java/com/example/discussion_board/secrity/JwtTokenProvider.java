package com.example.discussion_board.secrity;

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
public class JwtTokenProvider {

	private final String SECRET_KEY = "secret123secret123secret123secret12";
	private final SecretKey key;
	
	 
	/**
	 * コンストラクタ：文字列からSecretKeyを生成
	 */
	public JwtTokenProvider() {
		//文字列からSecretKeyを生成
		this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}
	
	/**
	 * JWTトークンを生成するメソッド
	 * @param email
	 * @return Jwtトークン
	 */
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	/**
	 * トークンからメールアドレス(subject)を取り出すメソッド
	 * @param token
	 * @return ログイン時に使用したemail
	 */
	public String  extractEmail(String token) {
		JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        return parser.parseClaimsJws(token)
                     .getBody()
                     .getSubject();
	}
	
	/**
	 * トークンの正当性チェック
	 * @param token
	 * @param email
	 * @return boolean 真:正当性あり 偽:正当性なし
	 */
	public boolean validateToken(String token, String email) {
		 // トークンから抽出したメールアドレスと一致しているか
        // かつ、有効期限が切れていないかをチェック
		return  extractEmail(token).equals(email) && !isTokenExpired(token);
	}
	
	/**
	 * トークンの有効期限切れを確認するメソッド
	 * @param token
	 * @return　真:期限切れ 偽:期限内
	 */
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
