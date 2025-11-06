package com.example.discussion_board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/login", // ログイン画面
								"/users/registration", // 登録画面
								"/api/users/**", 
								"/css/**", "/js/**", "/images/**")
						.permitAll()
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/login") // ← 独自ログイン画面
						.defaultSuccessUrl("/users/list", true) // ログイン成功後
						.permitAll())
	            .logout(logout -> logout
	                .logoutSuccessUrl("/login?logout")
	                .permitAll()
	            );

		return http.build();
	}

}
