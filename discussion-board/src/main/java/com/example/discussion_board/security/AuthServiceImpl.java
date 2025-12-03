package com.example.discussion_board.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.LoginResponse;
import com.example.discussion_board.dto.RefreshTokenResponse;
import com.example.discussion_board.dto.RefreshTokenWithPlain;
import com.example.discussion_board.entity.RefreshToken;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.exception.AuthException;
import com.example.discussion_board.repository.RefreshTokenRepository;
import com.example.discussion_board.repository.UserRepository;
import com.example.discussion_board.util.HashUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    
    // -------------------------------------------------------------------------------------
    // ① Login（アクセストークン & リフレッシュトークンの発行）
    // -------------------------------------------------------------------------------------
    @Override
    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        //ユーザーパスの照合
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException(
                    "invalid_credentials", "メールアドレスまたはパスワードが違います");
        }
        
        //アクセストークン発行
        String accessToken = jwtTokenProvider.generateToken(user.getEmail());

        // RefreshToken 発行
        RefreshTokenWithPlain refreshTokenWithPlain = refreshTokenService.createToken(user);
        
        //RefleshTokenEntity生成
        RefreshToken refreshToken = refreshTokenWithPlain.entity();

        return new LoginResponse(
            accessToken,
            refreshTokenWithPlain.plainToken(),
            refreshToken.getExpiresAt()
        );
    }


    // -------------------------------------------------------------------------------------
    // ② Refresh（リフレッシュトークンからアクセストークン再発行）
    // -------------------------------------------------------------------------------------
    @Override
    public RefreshTokenResponse refreshAccessToken(String refreshTokenPlain) {
    	
    	//平文トークンをハッシュ化
    	String hashed = HashUtil.sha256Base64(refreshTokenPlain);
        
        RefreshToken stored = refreshTokenRepository.findByTokenHash(hashed)
                .orElseThrow(() -> new AuthException("invalid_token", "リフレッシュトークンが無効です"));

        // ユーザー取得
        User user = userRepository.findById(stored.getUser().getId())
                .orElseThrow(() -> new AuthException("user_not_found", "ユーザーが存在しません"));

        // 新しいアクセストークンを生成
        String newAccessToken = jwtTokenProvider.generateToken(user.getEmail());

        // 新しい refreshToken も発行（安全のためローテーション）
        RefreshTokenWithPlain newRefreshTokenWithPlain = refreshTokenService.rotateToken(stored);
        RefreshToken newRefreshTokenEntity = newRefreshTokenWithPlain.entity();
       
        refreshTokenRepository.save(newRefreshTokenEntity);

        return new RefreshTokenResponse(newAccessToken, newRefreshTokenWithPlain.plainToken(), newRefreshTokenEntity.getExpiresAt());
    }

    // -------------------------------------------------------------------------------------
    // ③ Logout（RefreshToken を失効）
    // -------------------------------------------------------------------------------------
    @Override
    public void logout(String plainToken) {
        RefreshToken token = refreshTokenService.verifyRefreshToken(plainToken);

        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }
}
