package com.example.discussion_board.config;

import java.util.List;

import org.springframework.http.HttpMethod;

public record PermitPath(String path, HttpMethod method) {	
	/**
	 * 指定のリクエストパスとメソッドにマッチするか判定
	 * @param requestPath リクエストパス
	 * @param requestMethod リクエストメソッド
	 * @return マッチする場合 true
	 */
	public boolean matches(String requestPath, String requestMethod) {
		boolean methodMatches = (method == null || method.name().equalsIgnoreCase(requestMethod));
		return requestPath.startsWith(path) && methodMatches;
	}

	/**
	 * 下位パスも許可したい場合に追加のヘルパーメソッドなども作れる
	 */
	public boolean isSubPath(String requestPath) {
		return requestPath.startsWith(path);
	}
	
	// SecurityConfig で使うデフォルトの許可パスリスト
    public static List<PermitPath> defaultPermitPaths() {
        return List.of(
            new PermitPath("/api/auth/login", HttpMethod.POST),
            new PermitPath("/api/users", HttpMethod.POST),
            new PermitPath("/api/auth/refresh", HttpMethod.POST),
            new PermitPath("/login", HttpMethod.GET), 
            new PermitPath("/view/", HttpMethod.GET),
            new PermitPath("/css/", null),
            new PermitPath("/js/", null),
            new PermitPath("/images/", null),
            new PermitPath("/favicon.ico", null)
        );
    }
}
