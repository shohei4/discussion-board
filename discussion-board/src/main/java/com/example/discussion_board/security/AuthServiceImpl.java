package com.example.discussion_board.security;

import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.discussion_board.dto.LoginResponse;
import com.example.discussion_board.dto.RefreshTokenResponse;
import com.example.discussion_board.entity.RefreshToken;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.repository.RefreshTokenRepository;
import com.example.discussion_board.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final SecureRandom secureRandom = new SecureRandom();

    // -------------------------------------------------------------------------------------
    // ① Login（アクセストークン & リフレッシュトークンの発行）
    // -------------------------------------------------------------------------------------
    @Override
    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String accessToken = jwtTokenProvider.generateToken(user.getEmail());

        // RefreshToken 発行
        RefreshToken refreshToken = refreshTokenService.createToken(user);

        return new LoginResponse(
            accessToken,
            refreshToken.getPlainToken(),
            refreshToken.getExpiresAt()
        );
    }


    // -------------------------------------------------------------------------------------
    // ② Refresh（リフレッシュトークンからアクセストークン再発行）
    // -------------------------------------------------------------------------------------
    @Override
    public RefreshTokenResponse refeshAccessToken(String refreshTokenPlain) {

        String hashed = hashToken(refreshTokenPlain);

        RefreshToken stored = refreshTokenRepository.findByTokenHash(hashed)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // トークンの有効期限チェック
        if (stored.isExpired()) {
            refreshTokenRepository.delete(stored);
            throw new RuntimeException("Refresh token expired");
        }

        // ユーザー取得
        User user = userRepository.findById(stored.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 新しいアクセストークンを生成
        String newAccessToken = jwtTokenProvider.generateToken(user.getEmail());

        // 新しい refreshToken も発行（安全のためローテーション）
        String newPlainToken = UUID.randomUUID().toString();
        String newHashedToken = hashToken(newPlainToken);

        stored.setTokenHash(newHashedToken);
        stored.setExpiresAt(jwtTokenProvider.getRefreshTokenExpiry());
        refreshTokenRepository.save(stored);

        return new RefreshTokenResponse(newAccessToken, newPlainToken);
    }

    // -------------------------------------------------------------------------------------
    // ③ Logout（RefreshToken を失効）
    // -------------------------------------------------------------------------------------
    @Override
    public void logout(String refreshTokenPlain) {
        String hashed = hashToken(refreshTokenPlain);

        refreshTokenRepository.findByTokenHash(hashed)
                .ifPresent(refreshTokenRepository::delete);
    }

    // -------------------------------------------------------------------------------------
    // ④ Token ハッシュ化（平文 refresh token は DB に保存しない）
    // -------------------------------------------------------------------------------------
    private String hashToken(String token) {
        return passwordEncoder.encode(token); // ハッシュ化（BCryptなど）
    }
}
