package com.example.discussion_board.service;

import java.util.List;

import com.example.discussion_board.dto.GidaiRegistrationRequest;
import com.example.discussion_board.dto.GidaiResponse;
import com.example.discussion_board.dto.GidaiUpdateRequest;
import com.example.discussion_board.dto.GidaiUpdateResponse;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.model.Genre;

public interface GidaiService {
	
	/**
     * 議題を全件取得
     * @return GidaiResponse型のList
     */
    List<GidaiResponse> findAllGidai();

    /**
     * ID検索
     * @param id 議題ID
     * @return GidaiResponse型
     */
    GidaiUpdateResponse findByIdGidai(Long id);
    
    /**
     * ジャンル検索
     * @param genre
     * @return GidaiResponse型のList
     */
    List<GidaiResponse> findByGenre(Genre genre);

    /**
     * 議題を登録
     * @param request 登録用DTO（GidaiRegistrationRequest）
     * @return 登録後のGidaiResponse
     */
    GidaiResponse createGidai(GidaiRegistrationRequest request, User user);

    /**
     * 議題を更新
     * @param id 
     * @param request 更新用DTO（GidaiUpdateRequest）
     * @return 更新後のGidaiResponse
     */
    GidaiUpdateResponse editGidai(Long id, GidaiUpdateRequest request);
}
