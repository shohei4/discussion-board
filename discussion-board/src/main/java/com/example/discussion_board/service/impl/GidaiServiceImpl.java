package com.example.discussion_board.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.discussion_board.dto.GidaiRegistrationRequest;
import com.example.discussion_board.dto.GidaiResponse;
import com.example.discussion_board.dto.GidaiUpdateRequest;
import com.example.discussion_board.entity.Gidai;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.mapper.GidaiMapper;
import com.example.discussion_board.repository.GidaiRepository;
import com.example.discussion_board.service.GidaiService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GidaiServiceImpl implements GidaiService {
	
	private final GidaiRepository repository;
	private final GidaiMapper mapper;
	
	@Override
	public List<GidaiResponse> findAllGidai() {
		// TODO 自動生成されたメソッド・スタブ
		List<Gidai> gidaiList = repository.findAll();
		List<GidaiResponse> responseList = mapper.toResponseList(gidaiList);
		return responseList;
	}

	@Override
	public GidaiResponse findByIdGidai(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		Gidai gidai = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("指定の議題が存在しません"));
		GidaiResponse response = mapper.toResponse(gidai)
;		return response;
	}


	@Override
	public GidaiResponse createGidai(GidaiRegistrationRequest request, User user) {
		// TODO 自動生成されたメソッド・スタブ
		Gidai gidai = mapper.toEntity(request, user);
		repository.save(gidai);
		return mapper.toResponse(gidai);
	}

	@Override
	public GidaiResponse editGidai(GidaiUpdateRequest request) {
		// TODO 自動生成されたメソッド・スタブ
		
		Gidai gidai = repository.findById(request.getId())
				  .orElseThrow(() -> new RuntimeException("議題が存在しません"));
		
		mapper.updateEntity(gidai, request);
		
		repository.save(gidai);
		return mapper.toResponse(gidai);
	}

	@Override
	public void deleteGidai(Long id) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
