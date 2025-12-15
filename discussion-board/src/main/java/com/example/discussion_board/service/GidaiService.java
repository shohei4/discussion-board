package com.example.discussion_board.service;

import java.util.List;

import com.example.discussion_board.dto.GidaiRegistrationRequest;
import com.example.discussion_board.dto.GidaiResponse;
import com.example.discussion_board.dto.GidaiUpdateRequest;
import com.example.discussion_board.entity.User;

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
    GidaiResponse findByIdGidai(Long id);

    /**
     * 議題を登録
     * @param request 登録用DTO（GidaiRegistrationRequest）
     * @return 登録後のGidaiResponse
     */
    GidaiResponse createGidai(GidaiRegistrationRequest request, User user);

    /**
     * 議題を更新
     * @param request 更新用DTO（GidaiUpdateRequest）
     * @return 更新後のGidaiResponse
     */
    GidaiResponse editGidai(GidaiUpdateRequest request);

    /**
     * 議題を削除
     * @param id 削除対象の議題ID
     */
    void deleteGidai(Long id);
}
