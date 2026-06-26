package com.example.discussion_board.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.GidaiRegistrationRequest;
import com.example.discussion_board.dto.GidaiResponse;
import com.example.discussion_board.dto.GidaiUpdateRequest;
import com.example.discussion_board.entity.Gidai;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.exception.ResourceNotFoundException;
import com.example.discussion_board.exception.constant.GlobalErrorCode;
import com.example.discussion_board.mapper.GidaiMapper;
import com.example.discussion_board.model.Genre;
import com.example.discussion_board.repository.GidaiRepository;
import com.example.discussion_board.service.CurrentUserService;
import com.example.discussion_board.service.GidaiService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GidaiServiceImpl implements GidaiService {
	
	private final GidaiRepository repository;
	private final GidaiMapper mapper;
	private final CurrentUserService currentUserService;
	
	@Override
	public List<GidaiResponse> findAllGidai() {
		// TODO 自動生成されたメソッド・スタブ
		List<Gidai> gidaiList = repository.findAllByIsDeletedFalse();
	    if (gidaiList.isEmpty()) {
	        throw new ResourceNotFoundException(GlobalErrorCode.GIDAI_EMPTY);
	    }
	    return gidaiList.stream()
	    		//一覧表示ではeditableは非表示なのでfalseで固定
	            .map(gidai -> mapper.toResponse(gidai, false))
	            .collect(Collectors.toList());
	}

	@Override
	public GidaiResponse findByIdGidai(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		Gidai gidai = repository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException(GlobalErrorCode.GIDAI_EMPTY));
		//編集flagの取得
		boolean editable = currentUserService.isOwner(gidai.getUser().getId());
		GidaiResponse response = mapper.toResponse(gidai,editable)
;		return response;
	}
	
	@Override
	public List<GidaiResponse> findByGenre(Genre genre) {
		// TODO 自動生成されたメソッド・スタブ
		List<Gidai> gidaiList = repository.findByGenreAndIsDeletedFalse(genre);
		if (gidaiList.isEmpty()) {
	        throw new ResourceNotFoundException(GlobalErrorCode.GIDAI_EMPTY);
	    }
		return gidaiList.stream()
	            .map(gidai -> mapper.toResponse(gidai, false))
	            .collect(Collectors.toList());
	}


	@Override
	public GidaiResponse createGidai(GidaiRegistrationRequest request, User user) {
		// TODO 自動生成されたメソッド・スタブ
		Gidai gidai = mapper.toEntity(request, user);
		repository.save(gidai);
		return mapper.toResponse(gidai,true);
	}

	@Override
	public GidaiResponse editGidai(Long id, GidaiUpdateRequest request) {
		// TODO 自動生成されたメソッド・スタブ
		
		Gidai gidai = repository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException(GlobalErrorCode.GIDAI_EMPTY));
		
		mapper.updateEntity(gidai, request);
		
		repository.save(gidai);
		//編集flagの取得
		boolean editable = currentUserService.isOwner(gidai.getUser().getId());
		GidaiResponse response = mapper.toResponse(gidai, editable);
		return response;
	}

}
