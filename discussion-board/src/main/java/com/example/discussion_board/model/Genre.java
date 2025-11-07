package com.example.discussion_board.model;

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
	
	// 文字列からEnumを取得するユーティリティ
    public static Genre fromLabel(String label) {
        for (Genre g : Genre.values()) {
            if (g.getLabel().equals(label)) {
                return g;
            }
        }
        return OTHER; // デフォルト値
    }
	
}
