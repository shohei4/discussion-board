package com.example.discussion_board.dto;

import com.example.discussion_board.entity.RefreshToken;

import lombok.Builder;

/**
 * 生成したリフレッシュトークンのenitty要素とその平文を保持するオブジェクト
 * トークン生成時にentity要素と平文トークンをそれぞれ扱うため
 */
@Builder
public record RefreshTokenWithPlain(RefreshToken entity, String plainToken) {}
