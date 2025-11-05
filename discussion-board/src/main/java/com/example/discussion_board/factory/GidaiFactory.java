package com.example.discussion_board.factory;

import org.springframework.stereotype.Component;

import com.example.discussion_board.entity.Gidai;
import com.example.discussion_board.entity.User;
import com.example.discussion_board.model.Genre;

@Component
public class GidaiFactory {

	//FactoryメソッドでGidaiEntityを生成する
	public Gidai create(String gidai, Genre genre, User user) {
		Gidai entity = Gidai.builder()
				.user(user)
				.gidai(gidai)
				.genre(genre)
				.build();
		
		//createrAtを自動設定
		entity.onCreate();
		
		return entity;
	}

}
