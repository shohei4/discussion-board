package com.example.discussion_board.service;

public interface CurrentUserService {
	
	public Long getCurrentUserId();
	
	public boolean isOwner(Long userId) ;
}
