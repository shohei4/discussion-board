package com.example.discussion_board.domain.model;

public enum Genre {
	
	PHILOSOPHY("哲学"),
	HISTORY("歴史"),
	SOCIAL_SCIENCE("社会科学"),
	NATURAL_SCIENCE("自然科学"),
	ART("芸術・美術"),
	LITERATURE("文学"),
	LIFE("生活"),
	OTHER("その他");
	
	private final String label;
	
	Genre(String label) {
        this.label = label;
    }
	
	public String getLabel() {
        return label;
    }
	
}
