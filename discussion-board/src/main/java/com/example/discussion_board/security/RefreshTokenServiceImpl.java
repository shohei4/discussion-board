package com.example.discussion_board.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import com.example.discussion_board.dto.RefreshTokenWithPlain;
import com.example.discussion_board.entity.RefreshToken;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	private final RefreshToken refreshToken;
	private final RefreshTokenWithPlain refreshTokenWithPlain;
	

	 // RefreshToken を生成して保存
    @Override
    public RefreshTokenWithPlain createToken(User user) {
    	//1.平文トークン生成
        String plain = UUID.randomUUID().toString();
        //2.ハッシュ化
        String hashed = hash(plain);
        
        //3.RefreshTokenエンティティ生成
        RefreshToken token = RefreshToken.builder()
        		.user(user)
                .tokenHash(hashed)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plus(14, ChronoUnit.DAYS))
                .used(false)
                .revoked(false)
                .tokenVersion(1)
                .build();
        
        //4.DB保存
        refreshTokenRepository.save(token);

        
        //5.RefreshTokenBuilderWithPlainを生成
        RefreshTokenWithPlain tokenWithPlain = RefreshTokenWithPlain.builder()
                .entity(token)
                .plainToken(plain)
                .build();
        
        return tokenWithPlain;
    }

    // RefreshToken の検証
    @Override
    public RefreshToken verifyRefreshToken(String plainToken) {
        String hashed = hash(plainToken);

        RefreshToken token = refreshTokenRepository.findByTokenHash(hashed)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (token.isExpired() || token.isUsed() || token.isRevoked()) {
            throw new IllegalStateException("Refresh token is not valid");
        }

        return token;
    }

    // ローテーション（新しいトークンを発行し、古いのを used=true に）
    @Override
    public RefreshTokenWithPlain rotateToken(RefreshToken oldToken) {
    	
    	//1.古いトークンをuserd=trueにして保存
        oldToken.setUsed(true);
        refreshTokenRepository.save(oldToken);
        
        //2.新しいトークン生成
        RefreshTokenWithPlain newTokenWithPlain = createToken(oldToken.getUser());
        
        return newTokenWithPlain;
    }

    // 特定1つのRTを無効化
    @Override
    public void revokeToken(String plainToken) {
        String hashed = hash(plainToken);

        refreshTokenRepository.findByTokenHash(hashed)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });
    }

    // そのユーザーの全RTを無効化
    @Override
    public void revokeAllTokensForUser(Long userId) {
        List<RefreshToken> tokens = refreshTokenRepository.findAllByUserId(userId);

        for (RefreshToken t : tokens) {
            t.setRevoked(true);
        }
        refreshTokenRepository.saveAll(tokens);
    }

    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

	

}
