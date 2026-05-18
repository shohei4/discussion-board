package com.example.discussion_board.security;

import com.example.discussion_board.dto.RefreshTokenWithPlain;
import com.example.discussion_board.entity.RefreshToken;
import com.example.discussion_board.entity.User;

public interface RefreshTokenService {
	RefreshTokenWithPlain createToken(User user);

    RefreshToken verifyRefreshToken(String refreshToken);

    RefreshTokenWithPlain rotateToken(RefreshToken oldToken);

    void revokeToken(String refreshToken);

    void revokeAllTokensForUser(Long userId);
}
