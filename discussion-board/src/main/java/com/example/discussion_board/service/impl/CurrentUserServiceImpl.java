package com.example.discussion_board.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.discussion_board.security.CustomUserDetails;
import com.example.discussion_board.service.CurrentUserService;

/**
 * ログイン中のユーザーの権限処理を担うクラス
 */
@Service
public class CurrentUserServiceImpl implements CurrentUserService {

	@Override
	public Long getCurrentUserId() {
		Authentication auth = SecurityContextHolder
				.getContext()
				.getAuthentication();

		if (auth == null || !auth.isAuthenticated()) {
			return null;
		}

		Object principal = auth.getPrincipal();
		if (principal instanceof CustomUserDetails details) {
			return details.getId();
		}

		return null;
	}

	@Override
	public boolean isOwner(Long ownerUserId) {
		if (ownerUserId == null) {
			return false;
		}

		Long currentUserId = getCurrentUserId();
		return ownerUserId.equals(currentUserId);
	}

}
