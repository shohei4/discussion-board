package com.example.discussion_board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.discussion_board.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	/**DI*/
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
		http
            // CSRFは不要（ステートレス）
            .csrf(csrf -> csrf.disable())

            // セッションを使わない
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 認可設定
            .authorizeHttpRequests(auth -> {
                for (PermitPath p : PermitPath.defaultPermitPaths()) {
                    if (p.method() != null) {
                        auth.requestMatchers(p.method(), p.path() + "**").permitAll();
                    } else {
                        auth.requestMatchers(p.path() + "**").permitAll();
                    }
                }
                auth.anyRequest().authenticated();
            })
            
            .formLogin(form -> form
                    .loginPage("/view/login") // ← ここでカスタムログインページを指定
                    .loginProcessingUrl("/api/auth/login") 
                    .permitAll()
                )

            // JWTフィルターをUsernamePasswordAuthenticationFilterの前に追加
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
	
	//AuthenticationManagerをBean化
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
}
