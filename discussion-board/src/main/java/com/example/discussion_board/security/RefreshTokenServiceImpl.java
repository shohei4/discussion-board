package com.example.discussion_board.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.RefreshTokenWithPlain;
import com.example.discussion_board.entity.RefreshToken;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.exception.AuthException;
import com.example.discussion_board.repository.RefreshTokenRepository;
import com.example.discussion_board.util.HashUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // -------------------------------------------------------------------------------------
    // ① RefreshToken を生成して保存
    // -------------------------------------------------------------------------------------
    @Override
    public RefreshTokenWithPlain createToken(User user) {
        // 1. 平文トークン生成
        String plain = UUID.randomUUID().toString();

        // 2. ハッシュ化
        String hashed = HashUtil.sha256Base64(plain);

        // 3. RefreshToken エンティティ生成
        RefreshToken token = RefreshToken.builder()
                .user(user)
                .tokenHash(hashed)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plus(14, ChronoUnit.DAYS))
                .used(false)
                .revoked(false)
                .tokenVersion(1)
                .build();

        // 4. DB 保存
        refreshTokenRepository.save(token);

        // 5. RefreshTokenWithPlain を生成して返す
        return RefreshTokenWithPlain.builder()
                .entity(token)
                .plainToken(plain)
                .build();
    }

    // -------------------------------------------------------------------------------------
    // ② RefreshToken の検証
    // -------------------------------------------------------------------------------------
    @Override
    public RefreshToken verifyRefreshToken(String plainToken) {
        String hashed = HashUtil.sha256Base64(plainToken);

        RefreshToken token = refreshTokenRepository.findByTokenHash(hashed)
                .orElseThrow(() -> new AuthException("invalid_token", "リフレッシュトークンが無効です"));

        if (token.isExpired()) {
            throw new AuthException("token_expired", "リフレッシュトークンの期限が切れています");
        }
        if (token.isUsed() || token.isRevoked()) {
            throw new AuthException("revoked_token", "リフレッシュトークンは失効済みです");
        }

        return token;
    }

    // -------------------------------------------------------------------------------------
    // ③ ローテーション（新しいトークン発行、古いのを used=true に）
    // -------------------------------------------------------------------------------------
    @Override
    public RefreshTokenWithPlain rotateToken(RefreshToken oldToken) {
        if (oldToken.isUsed() || oldToken.isRevoked() || oldToken.isExpired()) {
            throw new AuthException("revoked_token", "古いリフレッシュトークンは無効です");
        }

        // 古いトークンを used=true に
        oldToken.setUsed(true);
        refreshTokenRepository.save(oldToken);

        // 新しいトークンを生成
        return createToken(oldToken.getUser());
    }

    // -------------------------------------------------------------------------------------
    // ④ 特定の RefreshToken を無効化
    // -------------------------------------------------------------------------------------
    @Override
    public void revokeToken(String plainToken) {
        String hashed = HashUtil.sha256Base64(plainToken);

        RefreshToken token = refreshTokenRepository.findByTokenHash(hashed)
                .orElseThrow(() -> new AuthException("invalid_token", "リフレッシュトークンが存在しません"));

        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }

    // -------------------------------------------------------------------------------------
    // ⑤ ユーザーの全 RefreshToken を無効化
    // -------------------------------------------------------------------------------------
    @Override
    public void revokeAllTokensForUser(Long userId) {
        List<RefreshToken> tokens = refreshTokenRepository.findAllByUserId(userId);

        for (RefreshToken t : tokens) {
            t.setRevoked(true);
        }
        refreshTokenRepository.saveAll(tokens);
    }
}
