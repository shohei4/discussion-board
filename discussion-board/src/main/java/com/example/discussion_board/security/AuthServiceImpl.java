package com.example.discussion_board.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.discussion_board.dto.LoginRequest;
import com.example.discussion_board.dto.LoginResponse;
import com.example.discussion_board.dto.RefreshTokenResponse;
import com.example.discussion_board.dto.RefreshTokenWithPlain;
import com.example.discussion_board.entity.RefreshToken;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.repository.RefreshTokenRepository;
import com.example.discussion_board.repository.UserRepository;
import com.example.discussion_board.util.HashUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final HashUtil hashUtil;
    
    // -------------------------------------------------------------------------------------
    // ① Login（アクセストークン & リフレッシュトークンの発行）
    // -------------------------------------------------------------------------------------
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        //ユーザーパスの照合
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
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
    public RefreshTokenResponse refeshAccessToken(String refreshTokenPlain) {
    	
    	//平文トークンをハッシュ化
    	String hashed = hashUtil.sha256Base64(refreshTokenPlain);
        
        RefreshToken stored = refreshTokenRepository.findByTokenHash(hashed)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // ユーザー取得
        User user = userRepository.findById(stored.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

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
    public void logout(String plainRefreshToken) {
        RefreshToken token = refreshTokenService.verifyRefreshToken(plainRefreshToken);

        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }
}
